package com.it.ymk.bubble.web.taobao;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.AbstractHttpMessage;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @Title TaobaoHttpLogin.java
 * @description TODO
 * @time 2016年9月22日 下午2:11:39
 * @author yanmingkun
 * @version 1.0
 *
 */
public class TaobaoHttpLogin {
    private static CookieStore sslcookies = new BasicCookieStore();
    private static CookieStore cookies    = new BasicCookieStore();

    /**
     *
     * @param isSSL
     * @return
     */
    public static CloseableHttpClient createSSLClientDefault(boolean isSSL) {
        if (isSSL) {
            SSLContext sslContext = null;
            try {
                sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                    // 信任所有
                    public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                        return true;
                    }
                }).build();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyStoreException e) {
                e.printStackTrace();
            }
            if (null != sslContext) {
                SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
                return HttpClients.custom().setSSLSocketFactory(sslsf).setDefaultCookieStore(sslcookies).build();
            }
            else {
                return HttpClients.custom().setDefaultCookieStore(cookies).build();
            }
        }
        else {
            return HttpClients.custom().setDefaultCookieStore(cookies).build();
        }
    }

    public static void headerWrapper(AbstractHttpMessage methord) {
        methord.setHeader("user-agent",
            "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
        methord.setHeader("accept-language", "zh-CN,zh;q=0.8");
        methord.setHeader("Accept-Encoding", "gzip, deflate, br");
        methord.setHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");

    }

    public static JSONObject String2Json(String string) {
        if (string == null) {
            return null;
        }
        String jsonString = string.trim();
        if (jsonString.startsWith("{") && jsonString.endsWith("}")) {
            JSONObject jb = null;
            try {
                jb = new JSONObject(jsonString);
                return jb;
            } catch (Exception e) {
                return null;
            }
        }
        else if (jsonString.startsWith("[") && jsonString.endsWith("]")) {
            JSONArray ja = null;
            try {
                ja = new JSONArray(jsonString);
                return (JSONObject) ja.get(0);
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public static String getCodeUrl(String TPL_username) {
        CloseableHttpClient httpClient = createSSLClientDefault(true);
        HttpPost hp = new HttpPost(
            "https://login.com.com.it.ymk.bubble.web.taobao.com/member/request_nick_check.do?_input_charset=utf-8");
        headerWrapper(hp);
        hp.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("username", TPL_username));
        HttpResponse httpresponse;
        try {
            hp.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            httpresponse = httpClient.execute(hp);
            HttpEntity entity = httpresponse.getEntity();
            String body = EntityUtils.toString(entity);
            System.out.println(body);
            EntityUtils.consume(entity);
            JSONObject J_obj = String2Json(body);
            boolean isNeed = (Boolean) J_obj.get("needcode");
            System.out.println("needcode:" + isNeed);
            if (isNeed) {
                String code_url = (String) J_obj.get("url");
                System.out.println("code_url:" + code_url);
                return code_url;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     *
     * @param url
     */
    public static void handleVilidateCode(String url) {
        CloseableHttpClient httpClient = createSSLClientDefault(true);
        HttpGet hg = new HttpGet(url);
        HttpResponse httpresponse;
        try {
            httpresponse = httpClient.execute(hg);
            HttpEntity entity = httpresponse.getEntity();
            InputStream content = entity.getContent();
            byte[] b = IOUtils.toByteArray(content);
            FileUtils.writeByteArrayToFile(new File("d://1.jpeg"), b);
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}