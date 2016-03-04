package com.adobe.dps.sample.api;

import com.adobe.dps.sample.model.ReferenceCursorPage;
import com.adobe.dps.sample.model.aggregated.Version;

public interface SampleService {

  String getPublication(String publicationId, Long version);

  ReferenceCursorPage getViews(String publicationId, Long version,
     String cursorMarker, int pageSize);

  String getView(String publicationId, String viewId,  Long version);

  ReferenceCursorPage getViewElements(String publicationId, String viewId,
      Long version, String cursorMarker, int pageSize);

  ReferenceCursorPage getViewContentElements(String publicationId, String viewId,
      Long version, String cursorMarker, int pageSize);
  
  ReferenceCursorPage getCollectionContentElements(String publicationId, String collectionId,
      Long version, String cursorMarker, int pageSize);
  
  String getLayout(String publicationId, String layoutId,
      Long version);

  String getCollection(String publicationId, String collectionId,
      Long version);

  String getArticle(String publicationId, String articleId,
      Long version);
  
  Version getPublicationVersion(String publicationId);
}
