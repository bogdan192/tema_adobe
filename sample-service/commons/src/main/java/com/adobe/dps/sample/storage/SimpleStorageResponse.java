/* ADOBE CONFIDENTIAL
 * Copyright 2014 Adobe Systems Incorporated. All rights reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Adobe Systems Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Adobe Systems Incorporated and its
 * suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Adobe Systems Incorporated.
 */

package com.adobe.dps.sample.storage;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.adobe.dps.sample.exceptions.storage.StorageException;
import com.adobe.dps.sample.utils.JsonDataMapper;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class SimpleStorageResponse implements ISimpleStorageResponse {

  private InputStream inputStream = null;
  private Class<?> classReference = null;

  @Override
  public InputStream getStream() {
    return inputStream;
  }

  @Override
  public SimpleStorageResponse withStream(InputStream inputStream) {
    this.inputStream = inputStream;
    return this;
  }

  @Override
  public void close() {
    if (inputStream != null) {
      IOUtils.closeQuietly(inputStream);
      inputStream = null;
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T deserialize() {
    try {
      return (T) new JsonDataMapper().getInstance().readValue(inputStream,
          classReference);
    } catch (JsonParseException e) {
      throw new StorageException(e);
    } catch (JsonMappingException e) {
      throw new StorageException(e);
    } catch (IOException e) {
      throw new StorageException(e);
    } finally {
      close();
    }
  }

  @Override
  public <T> ISimpleStorageResponse withClass(Class<T> classReference) {
    this.classReference = classReference;
    return this;
  }
}
