/* ADOBE CONFIDENTIAL
 * Copyright 2013 Adobe Systems Incorporated. All rights reserved.
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

package com.adobe.dps.sample.utils;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonDataMapper {

  private static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

  static {
    OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    OBJECT_MAPPER.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, false);
    OBJECT_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
    OBJECT_MAPPER.setVisibilityChecker(OBJECT_MAPPER.getSerializationConfig()
                                           .getDefaultVisibilityChecker()
                                           .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                                           .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                                           .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                                           .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
  }

  public ObjectMapper getInstance() {
    return OBJECT_MAPPER;
  }

  public String writeValueAsString(Object value)
      throws JsonGenerationException, JsonMappingException, IOException {
    return OBJECT_MAPPER.writeValueAsString(value);
  }

  public <T> T getObjectFromString(String content, Class<T> valueType)
      throws JsonParseException, JsonMappingException, IOException {
    return OBJECT_MAPPER.readValue(content, valueType);
  }

  public <T> T getObjectFromString(String content, TypeReference<?> typeReference)
      throws JsonParseException, JsonMappingException, IOException {
    return OBJECT_MAPPER.readValue(content, typeReference);
  }

  public <T> String generateJsonFromMap(Map<String, T> map) {
    Writer writer = new StringWriter();
    JsonGenerator jsonGenerator = null;

    try {
      jsonGenerator = new JsonFactory().createGenerator(writer);
      OBJECT_MAPPER.writeValue(jsonGenerator, map);
    } catch (IOException e) {
      if (jsonGenerator != null) {
        try {
          jsonGenerator.close();
        } catch (IOException e1) {
        }
      }
    }

    return writer.toString();
  }
}
