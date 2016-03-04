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

package com.adobe.dps.sample.exceptions;

public class ServiceException extends RuntimeException {
  private static final long serialVersionUID = 7577466581671261452L;

  private int statusCode;

  public ServiceException(String message, int statusCode) {
    super(message);
    this.statusCode = statusCode;
  }

  public ServiceException(String message, int statusCode, Throwable cause) {
    super(message, cause);
    this.statusCode = statusCode;
  }

  public ServiceException(int statusCode, Throwable cause) {
    super(cause);
    this.statusCode = statusCode;
  }

  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }
}
