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

package com.adobe.dps.sample.exceptions;

import javax.servlet.http.HttpServletResponse;

public class InternalServerException extends ServiceException {

  private static final long serialVersionUID = 1016630588935466344L;

  public InternalServerException(String message, Throwable cause) {
    super(message, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, cause);
  }

  public InternalServerException(String message) {
    super(message, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
  }
}
