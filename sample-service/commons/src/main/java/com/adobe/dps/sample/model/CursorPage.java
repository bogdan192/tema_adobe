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

package com.adobe.dps.sample.model;

public class CursorPage<T> extends Page<T> {
  private static final long serialVersionUID = 5451292521606705606L;
  private int pageSize;
  private String previousCursor;
  private String nextCursor;

  public CursorPage() {
    super();
  }

  public CursorPage(T items, int pageSize, String previousCursor, String nextCursor) {
    super(items);
    this.pageSize = pageSize;
    this.previousCursor = previousCursor;
    this.nextCursor = nextCursor;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public String getPreviousCursor() {
    return previousCursor;
  }

  public void setPreviousCursor(String previousCursor) {
    this.previousCursor = previousCursor;
  }

  public String getNextCursor() {
    return nextCursor;
  }

  public void setNextCursor(String nextCursor) {
    this.nextCursor = nextCursor;
  }
}
