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

package com.adobe.dps.sample.api.http.binding.mapper;

import com.adobe.dps.sample.exceptions.ServiceException;
import com.adobe.dps.sample.model.ErrorMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionMapperHandler implements ExceptionMapper<Exception> {

  private static final Logger logger = LoggerFactory
      .getLogger(ExceptionMapperHandler.class);

  public Response toResponse(Exception exception) {

    if (exception instanceof javax.ws.rs.WebApplicationException) {
      WebApplicationException e = (javax.ws.rs.WebApplicationException) exception;

      ErrorMessage message = new ErrorMessage(Response.Status.fromStatusCode(
          e.getResponse().getStatus()).toString(), exception.getMessage());

      logMessage(message); // simple troubleshooting for mock service, not meant
                           // to be so limited in production

      return Response.status(e.getResponse().getStatus())
          .type(MediaType.APPLICATION_JSON).entity(message).build();
    }

    if (exception instanceof ServiceException) {
      ServiceException cloudException = (ServiceException) exception;

      ErrorMessage message = new ErrorMessage(Response.Status.fromStatusCode(
          cloudException.getStatusCode()).toString(), exception.getMessage());

      logMessage(message); // simple troubleshooting for mock service, not meant
                           // to be so limited in production

      return Response.status(cloudException.getStatusCode())
          .type(MediaType.APPLICATION_JSON).entity(message).build();
    }

    ErrorMessage message = new ErrorMessage(Response.Status.fromStatusCode(
        Status.INTERNAL_SERVER_ERROR.getStatusCode()).toString(),
        "A catastropic error has occurred.");

    logMessage(message); // simple troubleshooting for mock service, not meant
                         // to be so limited in production

    return Response.status(Status.INTERNAL_SERVER_ERROR)
        .type(MediaType.APPLICATION_JSON).entity(message).build();
  }

  private void logMessage(ErrorMessage message) {
    logger.error(message.toString());
  }
}
