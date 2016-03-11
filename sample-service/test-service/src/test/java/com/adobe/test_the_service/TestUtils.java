package com.adobe.test_the_service;

import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;

import static java.util.Collections.sort;

public class TestUtils extends JerseyTest {
    private static String api = "http://localhost:8081";
    public WebResource wr = client().resource(api);
    private static final String resources = ".." + File.separator + "sample-service-mock" + File.separator + "src" +
            File.separator + "main" + File.separator + "resources" + File.separator + "data";
    public String[] valid_publications = getAvailablePublications();
    public String valid_version = "1405707625";

    @Override
    protected AppDescriptor configure() {
        return new WebAppDescriptor.Builder().build();
    }

    public ClientResponse getHrefResponse(String href) {
        return wr.path(href).get(ClientResponse.class);
    }

    public JSONObject getPublicationId(String id, String version) {
        JSONObject js;
        if (version == null) {
            js = wr.path("/publications/" + id).get(JSONObject.class);
        } else {
            js = wr.path("/publications/" + id + ";" + "version=" + version).get(JSONObject.class);
        }
        return js;
    }

    public ClientResponse getPublicationIdcr(String id, String version) {
        ClientResponse cr;
        if (version == null) {
            cr = wr.path("/publications/" + id).get(ClientResponse.class);
        } else {
            cr = wr.path("/publications/" + id + ";" + "version=" + version).get(ClientResponse.class);
        }
        return cr;
    }

    public JSONObject getViews(String publication_id, String viewId, String version) {
        JSONObject js;
        if (version == null) {
            js = wr.path("/" + publication_id + "/views/" + viewId).get(JSONObject.class);
        } else {
            js = wr.path("/" + publication_id + "/views/" + viewId + ";" + "version=" + version).get(JSONObject.class);
        }
        return js;
    }

    public ClientResponse getViewscr(String publication_id, String viewId, String version) {
        ClientResponse cr;
        if (version == null) {
            cr = wr.path("/" + publication_id + "/views/" + viewId).get(ClientResponse.class);
        } else {
            cr = wr.path("/" + publication_id + "/views/" + viewId + ";" + "version=" + version).get(ClientResponse.class);
        }
        return cr;
    }

    public ClientResponse serviceStatus() {
        return wr.path("/apidocs/").get(ClientResponse.class);
    }

    public void expectOKResponse(ClientResponse response) {
        Assert.assertNotNull(response);
        if (response.getStatus() != 200) {
            String entity = response.getEntity(String.class);
            Assert.assertEquals(200, response.getStatus());
        }
    }

    public void expectBadRequest(ClientResponse response) {
        Assert.assertNotNull(response);
        Assert.assertEquals(400, response.getStatus());
    }

    public void expectNotFound(ClientResponse response) {
        Assert.assertNotNull(response);
        Assert.assertEquals(404, response.getStatus());
    }

    public void expectUnauthorized(ClientResponse response) {
        Assert.assertNotNull(response);
        Assert.assertEquals(401, response.getStatus());
    }

    private String[] getAvailablePublications() {
        File dir = new File(resources);
        return dir.list();
    }

    protected String[] getAvailableVersionForPublication(String pub) {
        File dir = new File(resources + File.separator + pub);
        String[] directories = dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        sort(Arrays.asList(directories));
        return directories;
    }

    protected String[] getAvailableArticlesForVersion(String pub, String version) {
        File dir = new File(resources + File.separator + pub + File.separator + version + File.separator + "articles");
        return dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
    }

    protected String[] getAvailableViewsForVersion(String pub, String version) {
        File dir = new File(resources + File.separator + pub + File.separator + version + File.separator + "views");
        return dir.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
    }

}
