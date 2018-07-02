package com.it.ymk.component.httpclient.entity;

/**
 * @author yanmingkun
 * @date 2017-12-12 16:14
 */
public class HttpVO {
    private String url;
    private String entity;
    private String entityCharSet;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getEntityCharSet() {
        return entityCharSet;
    }

    public void setEntityCharSet(String entityCharSet) {
        this.entityCharSet = entityCharSet;
    }

    @Override
    public String toString() {
        return "HttpVO{" +
               "url='" + url + '\'' +
               ", entity='" + entity + '\'' +
               ", entityCharSet='" + entityCharSet + '\'' +
               '}';
    }
}
