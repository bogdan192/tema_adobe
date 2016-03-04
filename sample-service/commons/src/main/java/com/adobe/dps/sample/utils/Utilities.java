/*******************************************************************************
 * ADOBE CONFIDENTIAL
 *   ___________________
 *
 *    Copyright 2014 Adobe Systems Incorporated
 *    All Rights Reserved.
 *
 *   NOTICE:  All information contained herein is, and remains
 *   the property of Adobe Systems Incorporated and its suppliers,
 *   if any.  The intellectual and technical concepts contained
 *   herein are proprietary to Adobe Systems Incorporated and its
 *   suppliers and are protected by trade secret or copyright law.
 *   Dissemination of this information or reproduction of this material
 *   is strictly forbidden unless prior written permission is obtained
 *   from Adobe Systems Incorporated.
 ******************************************************************************/

package com.adobe.dps.sample.utils;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


public class Utilities {
    public static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

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

}
