package com.adobe.dps.sample.api.http.binding.cors;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

/**
 * Jersey filter that adds CORS headers to any HTTP response that goes through Jersey. Some notes:
 * <ul>
 *   <li>it adds the CORS headers on every response, even if they are not needed</li>
 *   <li>this is a naive implementation that allows incoming requests from any origin</li>
 *   <li>by default, only HEAD, GET and POST requests are allowed. Support for other HTTP methods needs to be
 *   explicitly enabled with the <i>Access-Control-Allow-Methods</i> header</li>
 * </ul>
 *
 * @author imargela@adobe.com
 */
@Provider
public class CorsFilter implements ContainerResponseFilter {

  @Override
  public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
      throws IOException {
    MultivaluedMap<String, Object> headers = responseContext.getHeaders();
    headers.add("Access-Control-Allow-Origin", "*");
  }
}
