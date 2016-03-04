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

package com.adobe.dps.sample.api.http.binding.controllers;

import com.adobe.dps.sample.api.SampleService;
import com.adobe.dps.sample.api.http.binding.pagination.AfterCursorPagination;
import com.adobe.dps.sample.api.http.binding.pagination.BeforeCursorPagination;
import com.adobe.dps.sample.api.http.binding.pagination.LinkHeaderCursorPagination;
import com.adobe.dps.sample.model.ReferenceCursorPage;
import com.adobe.dps.sample.model.aggregated.Article;
import com.adobe.dps.sample.model.aggregated.EntityReference;
import com.adobe.dps.sample.model.aggregated.Layout;
import com.adobe.dps.sample.model.aggregated.Publication;
import com.adobe.dps.sample.model.aggregated.View;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

/**
 * @author imargela@adobe.com
 */

@Path("/")
@Api(value = "Entities", position = 1, description = "Sample API")
@Component
@Singleton
public class SampleController {

  private static final Logger logger = LoggerFactory.getLogger(SampleController.class);

  public static final String PARAM_PUBLICATION_ID = "publicationId";
  public static final String PARAM_VIEW_ID = "viewId";
  public static final String PARAM_COLLECTION_ID = "collectionId";
  public static final String PARAM_LAYOUT_ID = "layoutId";
  public static final String PARAM_ARTICLE_ID = "articleId";
  public static final String PARAM_VERSION = "version";

  private static final String MEDIA_TYPE_VERSION = "v1.0";
  
  public static final String VERSIONING_LINK_DOCS = "This API follows <a href=\"http://stateless.co/hal_specification.html\" target=\"_blank\">the HAL specification</a>, so "
      + "resources in the API are linked in a consistent and discoverable way."
      + "<br><br>Links to child entities are versioned and you are guaranteed a consistent view of the entity graph by following these links. "
      + "<br><br>There are two special links <b>self</b> and <b>latestVersion</b>."
      + "<br>The <b>self</b> link returns the exact object and version you are currently viewing. "
      + "<br><br>The <b>latestVersion</b> link asks the service to return a newer version if it has one. Clients should compare the version coming back with the one in the current response to see if data was updated. "
      + "If a newer version of the entity was returned then links to child entities MAY also have been updated to newer versions and can be checked against their previous versions for example if you are updating a cache. "
      + "After following <b>latestVersion</b>, you are still guaranteed a consistent view through the object graph at the new snapshot version.<br>";

  @Inject
  private SampleService sampleService;

  @Context
  UriInfo uriInfo;

  public SampleController() {
    logger.debug("Created controller instance {}", this);
  }

  @GET
  @Path("/{publicationId}")
  @Consumes("application/vnd.adobe.publication." + MEDIA_TYPE_VERSION + "+json")
  @Produces("application/vnd.adobe.publication." + MEDIA_TYPE_VERSION + "+json")
  @ApiOperation(value = "Find publication by ID", response = Publication.class, httpMethod = "GET", 
    consumes = "application/vnd.adobe.publication." + MEDIA_TYPE_VERSION + "+json", 
    produces = "application/vnd.adobe.publication." + MEDIA_TYPE_VERSION + "+json",
    nickname = "entity version", 
    notes = "Operation to retrieve the publication entity. The publication is the entry point and a root of the object graph for all entities under a publication.<br>"
      + "A publication can be retrieved by its ID only or by its ID and a version. The version is not mandatory. If the version is missing, then the API will return the latest version of the publication."
      + "<br>"
      + VERSIONING_LINK_DOCS
      + "<br>Use our HAL browser (in Chrome) to see the API in action<br>"
      + "<b>HAL link:</b> "
      + "<a href=\"http://localhost:8080/hal/index.html#/publications/com.viewerdemo_win_tablet\">/publications/com.viewerdemo_win_tablet</a>", position = 1)
  @ApiResponses({ @ApiResponse(code = 400, message = "Bad request"),
      @ApiResponse(code = 404, message = "Publication not found") })
  public String getPublication(
      @ApiParam(value = "Publication ID", required = true, defaultValue = "com.viewerdemo_win_tablet") @PathParam(PARAM_PUBLICATION_ID) String publicationId,
      @ApiParam(value = "Version", required = false) @MatrixParam(PARAM_VERSION) Long version) {
    logger.debug("GET publication by publicationId={}", publicationId);

    return sampleService.getPublication(publicationId, version);
  }
  
  @GET
  @Path("/{publicationId}/views/{" + PARAM_VIEW_ID + "}")
  @ApiOperation(value = "Find view by publication ID and view ID",
    notes="Operation to retrieve a specific view entity.<br>"
          + "The version is not mandatory. If the version is missing, then the API will return the latest version of the view."
          + "<br>"
          + VERSIONING_LINK_DOCS 
          + "<br>Use our HAL browser (in Chrome) to see the API in action<br>"
          + "<b>HAL link:</b> "
          + "<a href=\"http://localhost:8080/hal/index.html#/publications/com.viewerdemo_win_tablet/views/browsepage001\">/publications/com.viewerdemo_win_tablet/views/browsepage001</a>",
    response = View.class, 
    position = 3)
  @ApiResponses({@ApiResponse(code = 400, message = "Bad request"),
                 @ApiResponse(code = 404, message = "Publication not found"),
                 @ApiResponse(code = 404, message = "View not found")})
  public String getViewV09(
      @ApiParam(value = "Publication ID", required = true, defaultValue = "com.viewerdemo_win_tablet") @PathParam(
          PARAM_PUBLICATION_ID) String publicationId,
      @ApiParam(value = "View ID", required = true, defaultValue = "browsepage001") @PathParam(PARAM_VIEW_ID) String viewId,
      @ApiParam(value = "Version", required = false) @MatrixParam(PARAM_VERSION) Long version) {
    logger.debug("GET view by viewId={}", viewId);
    
    return sampleService.getView(publicationId, viewId, version);
  }
  
  @GET
  @Path("/{publicationId}/views/{" + PARAM_VIEW_ID + "}/elements")
  @LinkHeaderCursorPagination
  @ApiOperation(value = "Get list of ordered view elements by publication ID and view ID", 
    notes="Operation to retrieve a specific view's <b>ordered</b> contents to be displayed in a context like a browsing page."
          + "The version is not mandatory. If the version is missing, then the API will return the elements of the latest version of the view.<br><br>"
          + "This API is paged with a fixed page size of 25. Requests that return multiple items will be paginated using cursors. "
          + "The Link header is used to return a set of ready-made links so the API clients don't have to construct pagination links themselves. "
          + "The API responds with pagination links that the API consumer will need to follow to get the previous or next pages. The pagination links "
          + "are regular API requests with additional query parameters called <i>before</i> and <i>after</i> that specify pagination request data.<br>"
          + "The before and after parameters are special markers that:"
          + "<ol>"
          + "<li>indicate the direction of the paging request (previous or next)</li>"
          + "<li>indicate the page starting or ending point</li>"
          + "</ol>"
          + "<br><br>"
          + "There is no default value for any of the two markers. The rules are simple:"
          + "<ol>"
          + "<li>if both parameters are missing, then the response will return a page with the latest results, limited to the fixed page size</li>"
          + "<li>if the after parameter is specified, then the response will return a page of results starting immediately after the element indicated by the parameter value</li>"
          + "<li>if the before parameter is specified, then the response will return a page of results ending immediately before the element indicated by the parameter value</li>"
          + "<li>if both parameters are specified, the request is invalid and the response will be a HTTP 400 Bad Request</li>"
          + "</ol>"
          + "<br>"
          + VERSIONING_LINK_DOCS 
          + "<br>Use our HAL browser (in Chrome) to see the API in action<br>"
          + "<b>HAL link:</b> "
          + "<a href=\"http://localhost:8080/hal/index.html#/publications/com.viewerdemo_win_tablet/views/browsepage001/elements\">/publications/com.viewerdemo_win_tablet/views/browsepage001/elements</a>",
    response = EntityReference.class,
    responseContainer = "List", 
    position = 4)
  @ApiResponses({@ApiResponse(code = 400, message = "Bad request"),
                 @ApiResponse(code = 404, message = "Publication not found"),
                 @ApiResponse(code = 404, message = "View not found")})
  public ReferenceCursorPage getViewElements(
      @ApiParam(value = "Publication ID", required = true, defaultValue = "com.viewerdemo_win_tablet") @PathParam(
          PARAM_PUBLICATION_ID) String publicationId,
      @ApiParam(value = "View ID", required = true, defaultValue = "browsepage001") @PathParam(PARAM_VIEW_ID) String viewId,
      @ApiParam(value = "Version", required = false) @MatrixParam(PARAM_VERSION) Long version,
      @BeanParam BeforeCursorPagination beforeCursorPagination,
      @BeanParam AfterCursorPagination afterCursorPagination) {
    logger.debug("GET view elements by viewId={}", viewId);

    String cursorMarker = (StringUtils.isNotEmpty(afterCursorPagination.getCursor())) ? afterCursorPagination.getCursor() : beforeCursorPagination.getCursor();
    return sampleService.getViewContentElements(publicationId,
                                                 viewId, version, cursorMarker,
                                                 AfterCursorPagination.DEFAULT_COUNT);
  }

  @GET
  @Path("/{publicationId}/layouts/{" + PARAM_LAYOUT_ID + "}")
  @Consumes("application/vnd.adobe.layout." + MEDIA_TYPE_VERSION + "+json")
  @Produces("application/vnd.adobe.layout." + MEDIA_TYPE_VERSION + "+json")
  @ApiOperation(value = "Find layout by publication ID and layout ID",
      consumes = "application/vnd.adobe.layout." + MEDIA_TYPE_VERSION + "+json", 
      produces = "application/vnd.adobe.layout." + MEDIA_TYPE_VERSION + "+json",
      notes = "Operation to retrieve a specific layout entity. <br>"
      + "The version is not mandatory. If the version is missing, then the API will return the latest version of the layout."
      + "<br>"
      + VERSIONING_LINK_DOCS
      + "<br>Use our HAL browser (in Chrome) to see the API in action<br>"
      + "<b>HAL link:</b> "
      + "<a href=\"http://localhost:8080/hal/index.html#/publications/com.viewerdemo_win_tablet/layouts/win_tablet\">/publications/com.viewerdemo_win_tablet/layouts/win_tablet</a>",
      response = Layout.class, position = 5)
  @ApiResponses({ @ApiResponse(code = 400, message = "Bad request"),
      @ApiResponse(code = 404, message = "Publication not found"),
      @ApiResponse(code = 404, message = "Layout not found") })
  public String getLayout(
      @ApiParam(value = "Publication ID", required = true, defaultValue = "com.viewerdemo_win_tablet") @PathParam(PARAM_PUBLICATION_ID) String publicationId,
      @ApiParam(value = "Layout ID", required = true, defaultValue = "win_tablet") @PathParam(PARAM_LAYOUT_ID) String layoutId,
      @ApiParam(value = "Version", required = false) @MatrixParam(PARAM_VERSION) Long version) {
    logger.debug("GET layout by layoutId={}", layoutId);

    return sampleService.getLayout(publicationId, layoutId, version);
  }

  @GET
  @Path("/{publicationId}/articles/{" + PARAM_ARTICLE_ID + "}")
  @Consumes("application/vnd.adobe.article." + MEDIA_TYPE_VERSION + "+json")
  @Produces("application/vnd.adobe.article." + MEDIA_TYPE_VERSION + "+json")
  @ApiOperation(value = "Find article by publication ID and article ID",
      consumes = "application/vnd.adobe.article." + MEDIA_TYPE_VERSION + "+json", 
      produces = "application/vnd.adobe.article." + MEDIA_TYPE_VERSION + "+json",
      notes = "Operation to retrieve a specific collection contents entity.<br>"
      + "The version is not mandatory. If the version is missing, then the API will return the latest version of the article."
      + "<br>"
      + VERSIONING_LINK_DOCS
      + "<br>Use our HAL browser (in Chrome) to see the API in action<br>"
      + "<b>HAL link:</b> "
      + "<a href=\"http://localhost:8080/hal/index.html#/publications/com.viewerdemo_win_tablet/articles/01_1906CV_cover\">/publications/com.viewerdemo_win_tablet/articles/01_1906CV_cover</a>",
      response = Article.class, position = 8, httpMethod = "GET")
  @ApiResponses({ @ApiResponse(code = 400, message = "Bad request"),
      @ApiResponse(code = 404, message = "Publication not found"),
      @ApiResponse(code = 404, message = "Article not found") })
  public String getArticleByFilter(
      @ApiParam(value = "Publication ID", required = true, defaultValue = "com.viewerdemo_win_tablet") @PathParam(PARAM_PUBLICATION_ID) String publicationId,
      @ApiParam(value = "Article ID", required = true, defaultValue = "01_1906CV_cover") @PathParam(PARAM_ARTICLE_ID) String articleId,
      @ApiParam(value = "Version", required = false) @MatrixParam(PARAM_VERSION) Long version) {
    logger.debug("GET article by articleId={}", articleId);

    return sampleService.getArticle(publicationId, articleId, version);
  }
}
