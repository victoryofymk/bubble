package com.it.ymk.image;

import java.io.*;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.IOUtils;

import com.asprise.ocr.Ocr;
//import com.asprise.util.ocr.OCR;

public class ReadImg {
    public static void main(String[] args) throws IOException {
        HttpClient httpClient = new HttpClient();
        GetMethod getMethod = new GetMethod("http://dz.bjjtgl.gov.cn/service/checkCode.do");
        //      GetMethod getMethod = new GetMethod("https://dynamic.12306.cn/otsweb/passCodeAction.do?rand=sjrand");
        int statusCode = httpClient.executeMethod(getMethod);
        if (statusCode != HttpStatus.SC_OK) {
            System.err.println("Method failed: " + getMethod.getStatusLine());
            return;
        }
        String picName = "/opt/ymk/product/webbas/";
        File filepic = new File(picName);
        if (!filepic.exists())
            filepic.mkdir();
        File filepicF = new File(picName + new Date().getTime() + ".png");
        InputStream inputStream = getMethod.getResponseBodyAsStream();
        OutputStream outStream = new FileOutputStream(filepicF);
        IOUtils.copy(inputStream, outStream);
        outStream.close();

        Ocr.setUp(); // one time setup
        Ocr ocr = new Ocr(); // create a new OCR engine
        ocr.startEngine("eng", Ocr.SPEED_FASTEST); // English
        String s = ocr.recognize(new File[] { filepicF }, Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT);
        System.out.println("Result: " + s);
        System.out
            .println("图片文字为:" + s.replace(",", "").replace("i", "1").replace(" ", "").replace("'", "").replace("o", "0")
                .replace("O", "0").replace("g", "6").replace("B", "8").replace("s", "5").replace("z", "2"));
        // ocr more images here ...
        ocr.stopEngine();
    }
}
