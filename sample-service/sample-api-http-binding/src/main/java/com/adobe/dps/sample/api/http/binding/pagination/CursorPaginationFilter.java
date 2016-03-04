package com.adobe.dps.sample.api.http.binding.pagination;

import com.adobe.dps.sample.exceptions.InternalServerException;
import com.adobe.dps.sample.model.ReferenceCursorPage;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.Provider;

/**
 * Filter that will add Link Headers to the response for the next/previous page. The filter will call {@link
 * com.adobe.dps.sample.model.CursorPage#getItems} on the response entity and set the result as the new response entity.
 *
 * @author imargela@adobe.com
 */
@LinkHeaderCursorPagination
@Provider
public class CursorPaginationFilter implements ContainerResponseFilter {

  private static final Logger logger = LoggerFactory
      .getLogger(CursorPaginationFilter.class);

  public static final String PARAM_CURSOR_AFTER = "after";
  public static final String PARAM_CURSOR_BEFORE = "before";
  public static final String PARAM_COUNT = "pageSize";

  public static final String NEXT_VALUE = "next";
  public static final String PREV_VALUE = "previous";

  @Override
  public void filter(ContainerRequestContext requestContext,
                     ContainerResponseContext responseContext) throws IOException {
    final ReferenceCursorPage page;

    // Verify the response entity in case another filter changed it.
    if (ReferenceCursorPage.class.isInstance(responseContext.getEntity())) {
      page = (ReferenceCursorPage) responseContext.getEntity();
    } else {
      logger.warn("Expected entity to be instance of CursorPage but got "
                  + responseContext.getEntityType());
      return;
    }

    // Unwrap the content from the page and set it as the response entity.
    // Risk: Because CursorPage is generic we don't know the exact type due to
    // type erasure. Jersey might need this to
    // correctly prioritize MessageBodyWriters. This doesn't affect
    // MessageBodyWriters<Object> which jackson and other
    // provider implement.
    // Generic types cannot be determined from the object or its class but we
    // could use the return type of the
    // ResourceMethod (e.g. CollectionController.getContents) that produced the
    // object(e.g. CursorPage<ContentElement>
    // where we can deduce CursorPage.getItems returns List<ContentElement>
    // using for example
    // http://docs.guava-libraries.googlecode.com/git/javadoc/com/google/common/reflect/TypeToken.html#resolveType(java.lang.reflect.Type)).
    responseContext.setEntity(page.getItems());

    Map<String, String> linkMap = new HashMap<>();
    if (page.getNextCursor() != null) {
      linkMap.put(NEXT_VALUE, page.getNextCursor());
    }

    if (page.getPreviousCursor() != null) {
      linkMap.put(PREV_VALUE, page.getPreviousCursor());
    }

    final MultivaluedMap<String, Object> headers = responseContext.getHeaders();

    String links = "";
    UriBuilder uriBuilder = requestContext.getUriInfo().getRequestUriBuilder();
    MultivaluedMap<String, String> keys = requestContext.getUriInfo()
        .getQueryParameters();
    uriBuilder.replaceQuery(null);

    for (Entry<String, List<String>> set : keys.entrySet()) {
      if (set.getKey().equals(PARAM_CURSOR_AFTER)
          || set.getKey().equals(PARAM_CURSOR_BEFORE)) {
        continue;
      }
      uriBuilder.queryParam(set.getKey(), set.getValue());
    }

    for (Entry<String, String> linkRel : linkMap.entrySet()) {
      UriBuilder localBuilder = uriBuilder.clone();

      if (linkRel.getKey().equals(NEXT_VALUE)) {
        localBuilder.queryParam(PARAM_CURSOR_AFTER, linkRel.getValue());
      } else if (linkRel.getKey().equals(PREV_VALUE)) {
        localBuilder.queryParam(PARAM_CURSOR_BEFORE, linkRel.getValue());
      } else {
        throw new InternalServerException("Could not correctly render the previous or next link values");
      }

      URI linkUri = localBuilder.build(linkMap.get(linkRel));
      Link link = Link.fromUri(linkUri).rel(linkRel.getKey()).build();
      links = links + link.toString() + ",";
    }
    if (!StringUtils.isEmpty(links)) {
      headers.add(HttpHeaders.LINK, links.substring(0, links.length() - 1));
    }
  }
}
