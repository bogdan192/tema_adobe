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

package com.adobe.dps.sample.api.http.binding.pagination;

import com.wordnik.swagger.annotations.ApiParam;

import javax.ws.rs.QueryParam;

public class BeforeCursorPagination {
  public static final int DEFAULT_COUNT = 5;

  @QueryParam(CursorPaginationFilter.PARAM_CURSOR_BEFORE)
  @ApiParam(value = "GUID of the previous content element loaded in the current page. " +
  		"The marker is a URL-encoded Base 64-encoded element identifier. " +
  		"An element identifier looks like this: 162384747212:0000000001:3012345. " +
  		"Note that it has 3 parts, separated by “:” The 1st part is the publication version, the 2nd part is the order of the element in the view and the 3rd part is the actual element ID. " +
  		"This value helps pinpoint the exact location of an element inside a view.", required = false)
  private String cursor;

  public String getCursor() {
    return cursor;
  }

  public void setCursor(String cursor) {
    this.cursor = cursor;
  }
  
  public int getCount() {
    return DEFAULT_COUNT;
  }
}
