package com.it.ymk.component.httpclient.entity;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author yanmingkun
 * @date 2017-12-12 17:33
 */
public class HttpUrlConnectionVO extends HttpVO implements Serializable {

    private static final long       serialVersionUID = -3507187330130202217L;

    private Integer                 connectTimeout;
    private Integer                 socketTimeout;

    private HashMap<String, String> requestHeader;

    public Integer getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(Integer connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public Integer getSocketTimeout() {
        return socketTimeout;
    }

    public void setSocketTimeout(Integer socketTimeout) {
        this.socketTimeout = socketTimeout;
    }

    public HashMap<String, String> getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(HashMap<String, String> requestHeader) {
        this.requestHeader = requestHeader;
    }

    @Override
    public String toString() {
        return super.toString() + "HttpUrlConnectionVO{" +
               "connectTimeout=" + connectTimeout +
               ", socketTimeout=" + socketTimeout +
               ", requestHeader=" + requestHeader +
               '}';
    }
}
