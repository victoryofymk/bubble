package com.it.ymk.image;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

/**
 * Created by yanmingkun on 2017/3/21.
 */
public class PicValid {
    private static CookieStore sslcookies = new BasicCookieStore();
    private static CookieStore cookies    = new BasicCookieStore();

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

    public static void handleVilidateCode(String url) {
        CloseableHttpClient httpClient = createSSLClientDefault(true);
        HttpGet hg = new HttpGet(url);
        HttpResponse httpresponse;
        try {
            httpresponse = httpClient.execute(hg);
            HttpEntity entity = httpresponse.getEntity();
            InputStream content = entity.getContent();
            byte[] b = IOUtils.toByteArray(content);
            FileUtils.writeByteArrayToFile(new File("/opt/ymk/product/webbas/1.PNG"), b);
            EntityUtils.consume(entity);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws TesseractException {
        System.setProperty("TESSDATA_PREFIX", "/usr/local/Cellar/tesseract/3.05.00/share/tessdata");
        String url = "http://shop.10086.cn/i/authImg";
        handleVilidateCode(url);
        File imageFile = new File("/opt/ymk/product/webbas/noiseRender.jpg");
        Tesseract instance = new Tesseract();
        instance.setDatapath("/usr/local/Cellar/tesseract/3.05.00/share/tessdata");

        //将验证码图片的内容识别为字符串
        String result = instance.doOCR(imageFile);
        System.out.print(result);
    }
}
