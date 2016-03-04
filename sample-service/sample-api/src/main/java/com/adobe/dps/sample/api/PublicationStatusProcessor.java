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

package com.adobe.dps.sample.api;

import com.adobe.dps.sample.exceptions.BadRequestException;

import java.util.HashMap;
import java.util.Map;

public class PublicationStatusProcessor {

  private Map<String, Long> publicationMap = new HashMap<String, Long>();

  public void setVersion(String publicationId, Long version) {
    if (version == null) {
      throw new BadRequestException("Version must be set to a valid value.");
    }

    publicationMap.put(publicationId, version);
  }

  public Long getCurrentVersion(String publicationId) {
    return publicationMap.get(publicationId);
  }
  
  public boolean hasDefaultVersion(String publicationId) {
    return publicationMap.containsKey(publicationId);
  }
}
