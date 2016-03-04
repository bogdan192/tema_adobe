/* ADOBE CONFIDENTIAL
 * Copyright 2012 Adobe Systems Incorporated. All rights reserved.
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

package com.adobe.dps.sample.exceptions.storage.aws;

import com.adobe.dps.sample.exceptions.ServiceException;

public class AmazonException extends ServiceException {
  private static final long serialVersionUID = 7577466581671261452L;

  public AmazonException(String message, int statusCode, Throwable cause) {
    super(message, statusCode, cause);
  }

  public AmazonException(String message, Throwable cause) {
    super(message, cause);
  }
}
