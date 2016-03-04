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
package com.adobe.dps.sample.api.http.binding.app;

import com.adobe.dps.sample.api.http.binding.cors.CorsFilter;
import com.adobe.dps.sample.api.http.binding.mapper.ExceptionMapperHandler;
import com.adobe.dps.sample.api.http.binding.pagination.CursorPaginationFilter;

import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.linking.DeclarativeLinkingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class SampleApplication extends ResourceConfig {

  public SampleApplication() {
    register(RequestContextFilter.class);
    register(JacksonFeature.class);
    register(ResourceListingProvider.class);
    register(ApiDeclarationProvider.class);
    register(ApiListingResourceJSON.class);
    register(CursorPaginationFilter.class);
    register(DeclarativeLinkingFeature.class);
    register(ExceptionMapperHandler.class);
    register(CorsFilter.class);
  }
}
