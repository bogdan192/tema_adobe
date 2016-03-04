package com.adobe.dps.sample.storage.localfs;

import java.io.InputStream;
import java.util.Map;

import javax.ws.rs.core.MediaType;

import com.adobe.dps.sample.storage.ISimpleStorage;
import com.adobe.dps.sample.storage.ISimpleStorageResponse;
import com.adobe.dps.sample.storage.SimpleStorageResponse;

/**
 * @author imargela@adobe.com
 */
public class LocalFileSystemStorage implements ISimpleStorage {

  @Override
  public <T> ISimpleStorageResponse getObject(String entityPath, Class<T> valueType) {
    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(entityPath);
    return new SimpleStorageResponse().withStream(stream).withClass(valueType);
  }

  @Override
  public <T> String putObject(String entityPath, T object, MediaType mediaType, Map<String, String> metadata) {
    throw new RuntimeException("Not implemented !");
  }

  @Override
  public <T> ISimpleStorageResponse getObject(String entityPath) {
    InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(entityPath);
    return new SimpleStorageResponse().withStream(stream);
  }
}
