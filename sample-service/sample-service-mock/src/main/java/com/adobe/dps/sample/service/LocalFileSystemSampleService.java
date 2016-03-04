/*******************************************************************************
 *
 *   ADOBE CONFIDENTIAL
 *    ___________________
 *
 *   Copyright 2014 Adobe Systems Incorporated
 *   All Rights Reserved.
 *
 *    NOTICE:  All information contained herein is, and remains
 *    the property of Adobe Systems Incorporated and its suppliers,
 *    if any.  The intellectual and technical concepts contained
 *    herein are proprietary to Adobe Systems Incorporated and its
 *    suppliers and are protected by trade secret or copyright law.
 *    Dissemination of this information or reproduction of this material
 *    is strictly forbidden unless prior written permission is obtained
 *    from Adobe Systems Incorporated.
 *
 ******************************************************************************/
package com.adobe.dps.sample.service;

import com.adobe.dps.sample.api.PublicationStatusProcessor;
import com.adobe.dps.sample.api.SampleService;
import com.adobe.dps.sample.exceptions.InternalServerException;
import com.adobe.dps.sample.exceptions.NotFoundException;
import com.adobe.dps.sample.model.ReferenceCursorPage;
import com.adobe.dps.sample.model.aggregated.EntityReference;
import com.adobe.dps.sample.model.aggregated.Version;
import com.adobe.dps.sample.storage.ISimpleStorage;
import com.adobe.dps.sample.storage.ISimpleStorageResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * @author imargela@adobe.com
 */
public class LocalFileSystemSampleService implements
                                                  SampleService {

  private ISimpleStorage storage;
  private PublicationStatusProcessor processor;

  public LocalFileSystemSampleService(ISimpleStorage storage,
                                      PublicationStatusProcessor processor) {
    this.storage = storage;
    this.processor = processor;
  }

  @Override
  public String getPublication(String publicationId, Long version) {
    return getData(
        String.format("data/%s/%s/data.json", publicationId,
            checkVersion(publicationId, version)));
  }

  @Override
  public ReferenceCursorPage getViews(String publicationId, Long version,
      String cursorMarker, int pageSize) {
    return new ReferenceCursorPage(getData(String.format(
        "data/%s/%s/views/data.json", publicationId,
        checkVersion(publicationId, version)), EntityReference.class),
        pageSize, cursorMarker, cursorMarker);
  }

  @Override
  public String getView(String publicationId, String viewId, Long version) {
    return getData(String.format("data/%s/%s/views/%s/data.json",
        publicationId, checkVersion(publicationId, version), viewId));
  }

  @Override
  public ReferenceCursorPage getViewElements(String publicationId,
      String viewId, Long version, String cursorMarker, int pageSize) {

    String fileName = (StringUtils.isEmpty(cursorMarker)) ? "contents.json"
        : cursorMarker + ".json";

    String path = String.format("data/%s/%s/views/%s/%s", publicationId,
        checkVersion(publicationId, version), viewId, fileName);

    return getCollectionPage(path, version, cursorMarker, pageSize);
  }

  @Override
  public ReferenceCursorPage getViewContentElements(String publicationId,
    String viewId, Long version, String cursorMarker, int pageSize) {
    String fileName = (StringUtils.isEmpty(cursorMarker)) ? "data.json"
        : cursorMarker + ".json";

    String path = String.format("data/%s/%s/views/contentElements/%s/%s", publicationId,
        checkVersion(publicationId, version), viewId, fileName);

    return getCollectionPage(path, version, cursorMarker, pageSize);
  }
  
  @Override
  public ReferenceCursorPage getCollectionContentElements(String publicationId,
      String collectionId, Long version, String cursorMarker, int pageSize) {
    String fileName = (StringUtils.isEmpty(cursorMarker)) ? "data.json"
        : cursorMarker + ".json";

    String path = String.format("data/%s/%s/collections/contentElements/%s/%s", publicationId,
        checkVersion(publicationId, version), collectionId, fileName);

    return getCollectionPage(path, version, cursorMarker, pageSize);
  }
  
  @Override
  public String getLayout(String publicationId, String layoutId, Long version) {
    return getData(String.format("data/%s/%s/layouts/%s/data.json",
        publicationId, checkVersion(publicationId, version), layoutId));
  }

  @Override
  public String getCollection(String publicationId, String collectionId,
      Long version) {
    return getData(String.format("data/%s/%s/collections/%s/data.json",
        publicationId, checkVersion(publicationId, version), collectionId));
  }

  private ReferenceCursorPage getCollectionPage(String fileName, Long version,
      String cursorMarker, int pageSize) {

    EntityReference reference = getData(fileName, EntityReference.class);

    // hack, would never do in production, just for demo purposes!!!!
    String prevRef = getMarker(reference, "prev");
    String nextRef = getMarker(reference, "next");

    String previousCursor = getParameterValue(prevRef, "before", null);
    String nextCursor = getParameterValue(nextRef, "after", null);

    return new ReferenceCursorPage(reference, pageSize, previousCursor,
        nextCursor);
  }

  @Override
  public String getArticle(String publicationId, String articleId, Long version) {
    return getData(String.format("data/%s/%s/articles/%s/data.json",
        publicationId, checkVersion(publicationId, version), articleId));
  }

  @Override
  public Version getPublicationVersion(String publicationId) {
    return getData(String.format("data/%s/version.json", publicationId),
        Version.class);
  }

  private <T> T getData(String objectPath, Class<T> objectClass) {
    ISimpleStorageResponse response = null;
    T object = null;

    try {
      response = storage.getObject(objectPath, objectClass);

      if (response == null || response.getStream() == null) {
        throw new NotFoundException("Could not locate resource with path="
            + objectPath);
      }
      object = response.deserialize();
    } finally {
      if (response != null) {
        response.close();
      }
    }

    return object;
  }

  private String getData(String objectPath) {
    ISimpleStorageResponse response = null;
    String data = null;

    try {
      response = storage.getObject(objectPath);

      if (response == null || response.getStream() == null) {
        throw new NotFoundException("Could not locate resource with path="
            + objectPath);
      }

      StringWriter writer = new StringWriter();
      IOUtils.copy(response.getStream(), writer, StandardCharsets.UTF_8);
      data = writer.toString();
    } catch (IOException e) {
      throw new InternalServerException(
          "Could not correctly serialize outgoing data field");
    } finally {
      if (response != null) {
        response.close();
      }
    }

    return data;
  }

  private Long checkVersion(String publicationId, Long version) {

    if (version == null) {
      if (!processor.hasDefaultVersion(publicationId)) {
        Version currentVersion = getData(
            String.format("data/%s/version.json", publicationId), Version.class);
        processor.setVersion(publicationId, currentVersion.getVersion());
      }

      return processor.getCurrentVersion(publicationId);
    }

    return version;
  }

  // hack, would never do in production, just for demo purposes!!!!
  @SuppressWarnings("unchecked")
  private String getMarker(EntityReference reference, String linkName) {
    HashMap<Object, Object> maps = (HashMap<Object, Object>) reference
        .getAdditionalProperties().get("_links");

    if (maps == null) { // cursor is okay with handling null values
      return null;
    }

    HashMap<String, String> data = (HashMap<String, String>) maps.get(linkName);

    if (data == null) {
      return null;
    }

    String href = data.get("href");

    return href;
  }

  // hack, would never do in production, just for demo purposes!!!! yuck!
  private String getParameterValue(String href, String key, String defaultValue) {

    if (StringUtils.isEmpty(href)) {
      return defaultValue;
    }

    URI uri = URI.create(href);
    String query = uri.getQuery();

    if (query == null) {
      return defaultValue;
    }

    String[] fields = query.split("&");
    for (String field : fields) {

      String[] data = field.split("=", 2);
      if (data[0].equals(key)) {
        return data[1];
      }
    }

    return defaultValue;
  }

}
