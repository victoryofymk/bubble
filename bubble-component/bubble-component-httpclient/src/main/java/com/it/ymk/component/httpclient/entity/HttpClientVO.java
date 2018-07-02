package com.it.ymk.component.httpclient.entity;

import java.io.Serializable;
import java.util.Arrays;

import org.apache.http.Header;
import org.apache.http.client.config.RequestConfig;

/**
 * @author yanmingkun
 * @date 2017-12-12 17:33
 */
public class HttpClientVO extends HttpVO implements Serializable {

    private static final long serialVersionUID = 3346064757706396144L;

    private Boolean           disableAutomaticRetry;
    private Integer           retryTimes;
    private Boolean           requestSentRetryEnabled;

    private Header[]          headers;
    private RequestConfig     requestConfig;

    public Boolean isDisableAutomaticRetry() {
        return disableAutomaticRetry;
    }

    public void setDisableAutomaticRetry(Boolean disableAutomaticRetry) {
        this.disableAutomaticRetry = disableAutomaticRetry;
    }

    public Integer getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

    public Boolean isRequestSentRetryEnabled() {
        return requestSentRetryEnabled;
    }

    public void setRequestSentRetryEnabled(Boolean requestSentRetryEnabled) {
        this.requestSentRetryEnabled = requestSentRetryEnabled;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public void setHeaders(Header[] headers) {
        this.headers = headers;
    }

    public RequestConfig getRequestConfig() {
        return requestConfig;
    }

    public void setRequestConfig(RequestConfig requestConfig) {
        this.requestConfig = requestConfig;
    }

    @Override
    public String toString() {
        return super.toString() + "HttpClientVO{" +
               "disableAutomaticRetry=" + disableAutomaticRetry +
               ", retryTimes=" + retryTimes +
               ", requestSentRetryEnabled=" + requestSentRetryEnabled +
               ", headers=" + Arrays.toString(headers) +
               ", requestConfig=" + requestConfig +
               '}';
    }
}
