package com.it.ymk.component.httpclient.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.it.ymk.component.httpclient.constant.HttpConstant;
import com.it.ymk.component.httpclient.entity.HttpClientVO;
import com.it.ymk.component.httpclient.entity.HttpResponseVO;
import com.it.ymk.component.httpclient.entity.HttpUrlConnectionVO;

/**
 * 采用http 提交请求
 *
 * @author yanmingkun
 * @date 2017-12-12 16:11
 */
public class HttpClient {

    /**
     * 以apache httpclient 方式提交请求
     *
     * @param httpClientVO
     * @return HttpResponse
     * @throws IOException
     */
    public HttpResponseVO httpPostByApache(HttpClientVO httpClientVO) throws IOException {

        HttpResponseVO httpResponseVO = new HttpResponseVO();

        // 将参数传入post方法中
        HttpPost httpPost = new HttpPost(httpClientVO.getUrl());
        httpPost.setEntity(new StringEntity(httpClientVO.getEntity(), httpClientVO.getEntityCharSet()));
        httpPost.setHeaders(httpClientVO.getHeaders());
        httpPost.setConfig(httpClientVO.getRequestConfig());

        HttpResponse httpResponse = httpPostByApache(httpPost);

        int code = httpResponse.getStatusLine().getStatusCode();
        httpResponseVO.setCode(code);
        if (HttpStatus.SC_OK == code) {
            httpResponseVO.setData(EntityUtils.toString(httpResponse.getEntity(), httpClientVO.getEntityCharSet()));
        }
        else {
            httpResponseVO.setMsg(httpResponse.getStatusLine().getReasonPhrase());
        }

        return httpResponseVO;
    }

    /**
     * 以apache httpclient 方式提交请求
     *
     * @param httpPost
     * @return HttpResponse
     * @throws IOException
     */
    public HttpResponse httpPostByApache(HttpPost httpPost) throws IOException {

        return httpPostByApache(httpPost, false, 0, false);
    }

    /**
     * 以apache httpclient 方式提交请求
     *
     * @param httpPost
     * @return HttpResponse
     * @throws IOException
     */
    public HttpResponse httpPostByApache(HttpPost httpPost, boolean disableAutomaticRetry, int retryTimes,
        boolean requestSentRetryEnabled) throws IOException {

        // 将参数传入post方法中

        CloseableHttpClient httpClient = buildHttpClient(disableAutomaticRetry, retryTimes, requestSentRetryEnabled);

        HttpResponse httpResponse = httpClient.execute(httpPost);

        return httpResponse;
    }

    /**
     * 以httpUrlConnection提交请求
     *
     * @param httpUrlConnectionVO
     * @return
     */
    public HttpResponseVO httpPostByJdk(HttpUrlConnectionVO httpUrlConnectionVO) {
        OutputStream out = null;
        InputStream is = null;
        HttpURLConnection con;
        HttpResponseVO httpResponseVO = new HttpResponseVO();
        try {
            //创建URL
            URL url = new URL(httpUrlConnectionVO.getUrl());
            //建立连接，并将连接强转为Http连接
            URLConnection conn = url.openConnection();
            con = (HttpURLConnection) conn;

            //设置请求方式和请求头：
            con.setConnectTimeout(httpUrlConnectionVO.getConnectTimeout());
            con.setReadTimeout(httpUrlConnectionVO.getSocketTimeout());
            con.setDoInput(HttpConstant.REQUEST_DO_INPOUT);
            con.setDoOutput(HttpConstant.REQUEST_DO_OUTPOUT);
            con.setRequestMethod(HttpConstant.REQUEST_METHOD_POST);
            //设置请求头参数
            Iterator iter = httpUrlConnectionVO.getRequestHeader().entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, String> entry = (Map.Entry) iter.next();
                con.setRequestProperty(entry.getKey(), entry.getValue());
            }

            //获得输出流
            out = con.getOutputStream();
            out.write(httpUrlConnectionVO.getEntity().getBytes(httpUrlConnectionVO.getEntityCharSet()));
            out.close();

            int code = con.getResponseCode();
            httpResponseVO.setCode(code);
            //服务端返回正常
            getHttpResponse(httpResponseVO, con, is, code);

            con.disconnect();

        } catch (ProtocolException e) {
        } catch (MalformedURLException e) {
        } catch (UnsupportedEncodingException e) {
        } catch (IOException e) {
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return httpResponseVO;
    }

    /**
     * 通过httpUrlConnection模拟form表单提交
     *
     * @param httpUrlConnectionVO
     * @return
     */
    public HttpResponseVO httpPostFormByJdk(HttpUrlConnectionVO httpUrlConnectionVO) throws IOException {
        HttpResponseVO httpResponseVO = new HttpResponseVO();
        URL url = new URL(httpUrlConnectionVO.getUrl());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 提交模式
        conn.setRequestMethod(HttpConstant.REQUEST_METHOD_POST);
        // 连接超时 单位毫秒
        if (httpUrlConnectionVO.getConnectTimeout() != 0) {
            conn.setConnectTimeout(httpUrlConnectionVO.getConnectTimeout());
        }
        else {
            conn.setConnectTimeout(30000);
        }

        // 读取超时 单位毫秒
        if (httpUrlConnectionVO.getSocketTimeout() != 0) {
            conn.setReadTimeout(httpUrlConnectionVO.getSocketTimeout());
        }
        else {
            conn.setReadTimeout(30000);
        }
        // 是否输入参数
        conn.setDoOutput(true);
        byte[] bypes = httpUrlConnectionVO.getEntity().getBytes(httpUrlConnectionVO.getEntityCharSet());
        // 输入参数
        conn.getOutputStream().write(bypes);
        InputStream is = conn.getInputStream();
        int code = conn.getResponseCode();
        httpResponseVO.setCode(code);
        //服务端返回正常
        getHttpResponse(httpResponseVO, conn, is, code);

        conn.disconnect();
        return httpResponseVO;
    }

    private void getHttpResponse(HttpResponseVO httpResponseVO, HttpURLConnection conn, InputStream is, int code)
        throws IOException {
        if (HttpConstant.RESPONSE_STATUS_200 == code) {

            StringBuffer sb = getFromInputStream(conn.getInputStream());
            httpResponseVO.setData(sb.toString());
            is.close();

        }
        else {
            httpResponseVO.setMsg(conn.getResponseMessage());
        }
    }

    /**
     * 从输入流读取数据
     *
     * @param is
     * @return
     * @throws IOException
     */
    private StringBuffer getFromInputStream(InputStream is) throws IOException {
        byte[] b = new byte[1024];
        StringBuffer sb = new StringBuffer();
        int len;
        while ((len = is.read(b)) != -1) {
            String str = new String(b, 0, len, HttpConstant.ENTITY_CHARSET_UTF_8);
            sb.append(str);

        }
        return sb;
    }

    /**
     * 创建httpClient
     *
     * @param disableAutomaticRetry
     * @param retryTimes
     * @param requestSentRetryEnabled
     * @return CloseableHttpClient
     */
    private CloseableHttpClient buildHttpClient(boolean disableAutomaticRetry, int retryTimes,
        boolean requestSentRetryEnabled) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        if (disableAutomaticRetry) {
            httpClientBuilder.disableAutomaticRetries();
            return httpClientBuilder.build();
        }
        if (requestSentRetryEnabled) {
            httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(retryTimes, requestSentRetryEnabled));
            return httpClientBuilder.build();
        }
        return httpClientBuilder.build();
    }

}
