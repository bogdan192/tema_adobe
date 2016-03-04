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

import javax.servlet.http.HttpServletResponse;

public class BadRequestException extends ServiceException {
  private static final long serialVersionUID = 2257051145260290778L;

  public BadRequestException(String message, Throwable cause) {
    super(message, HttpServletResponse.SC_BAD_REQUEST, cause);
  }

  public BadRequestException(String message) {
    super(message, HttpServletResponse.SC_BAD_REQUEST);
  }
}
