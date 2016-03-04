package com.adobe.dps.sample.model;

import com.adobe.dps.sample.model.aggregated.EntityReference;

/**
 * @author imargela@adobe.com
 */
public class ReferenceCursorPage extends CursorPage<EntityReference> {

  private static final long serialVersionUID = 274388453367203959L;

  public ReferenceCursorPage(EntityReference referenceList, int pageSize, String previousCursor, String nextCursor) {
    super(referenceList, pageSize, previousCursor, nextCursor);
  }
}
