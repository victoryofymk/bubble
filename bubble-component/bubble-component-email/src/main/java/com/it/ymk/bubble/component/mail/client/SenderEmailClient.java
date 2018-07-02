package com.it.ymk.bubble.component.mail.client;

import java.util.*;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.it.ymk.bubble.component.mail.AuthenticatorGenerator;
import com.it.ymk.bubble.component.mail.entity.SenderMailVO;

public class SenderEmailClient {
    static Logger               logger       = LoggerFactory.getLogger(ReceiveEmailClient.class);
    private static String       HOST         = "smtp.139.com";
    private static String       AUTH         = "true";
    private static final String smtpPort     = "25";
    private static final String senderAddr   = null;
    private static final String smtpNeedAuth = "true";
    private static final String smtpQuitWait = "false";
    private static final String isDebug      = "false";

    /**   
     * 以文本格式发送邮件   
     * 
     * @param mailInfo 待发送的邮件的信息   
     */
    public static boolean sendTextMail(SenderMailVO mailInfo) {
        // 判断是否需要身份认证  
        //        String smtpHost = SystemConfig.getProperty("mail.smtp.host", HOST);
        String smtpHost = "";
        Authenticator authenticator = AuthenticatorGenerator.getAuthenticator(mailInfo.getSender(),
            mailInfo.getPassword());
        Properties pro = new Properties();
        pro.put("mail.smtp.host", smtpHost);
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session     
        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        try {
            // 根据session创建一个邮件消息     
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址     
            Address from = new InternetAddress(mailInfo.getSender());
            // 设置邮件消息的发送者     
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中     
            String[] toAddress = mailInfo.getReceiver().split(";");
            if (toAddress.length > 1) {
                Address[] address = new InternetAddress[toAddress.length];
                for (int i = 0; i < toAddress.length; i++) {
                    address[i] = new InternetAddress(toAddress[i]);
                }
                mailMessage.setRecipients(Message.RecipientType.TO, address);
            }
            else {
                Address to = new InternetAddress(mailInfo.getReceiver());
                // Message.RecipientType.TO属性表示接收者的类型为TO     
                mailMessage.setRecipient(Message.RecipientType.TO, to);
            }
            // 设置邮件消息的主题     
            mailMessage.setSubject(mailInfo.getTheme());
            // 设置邮件消息发送的时间     
            mailMessage.setSentDate(new Date());
            // 设置邮件消息的主要内容     
            String mailContent = mailInfo.getContent();
            mailMessage.setText(mailContent);
            // 发送邮件     
            Transport.send(mailMessage);
            return true;
        } catch (Exception e) {
            logger.error("发送失败！", e);
            return false;
        }

    }

    /**   
      * 以HTML格式发送邮件   
      * 
      * @param mailInfo 待发送的邮件信息   
      * @throws Exception
      */
    public static boolean sendHtmlMail(SenderMailVO mailInfo) throws Exception {
        /*String smtpHost = SystemConfig.getProperty("mail.smtp.host", HOST);
        String auth=SystemConfig.getProperty("mail.smtp.auth", AUTH);*/
        String smtpHost = "";
        String auth = "";
        Authenticator authenticator = AuthenticatorGenerator.getAuthenticator(mailInfo.getSender(),
            mailInfo.getPassword());
        Properties pro = new Properties();
        pro.put("mail.smtp.host", smtpHost);
        pro.put("mail.smtp.auth", auth);
        // 根据邮件会话属性和密码验证器构造一个发送邮件的session     
        Session sendMailSession = Session.getInstance(pro, authenticator);

        try {
            // 根据session创建一个邮件消息     
            Message mailMessage = new MimeMessage(sendMailSession);
            // 创建邮件发送者地址     
            Address from = new InternetAddress(mailInfo.getSender());
            // 设置邮件消息的发送者     
            mailMessage.setFrom(from);
            // 创建邮件的接收者地址，并设置到邮件消息中     
            String[] toAddress = mailInfo.getReceiver().split(";");
            if (toAddress.length > 1) {
                Address[] address = new InternetAddress[toAddress.length];
                for (int i = 0; i < toAddress.length; i++) {
                    address[i] = new InternetAddress(toAddress[i]);
                }
                mailMessage.setRecipients(Message.RecipientType.TO, address);
            }
            else {
                Address to = new InternetAddress(mailInfo.getReceiver());
                // Message.RecipientType.TO属性表示接收者的类型为TO     
                mailMessage.setRecipient(Message.RecipientType.TO, to);
            }

            // 设置邮件消息的主题     
            mailMessage.setSubject(mailInfo.getTheme());
            // 设置邮件消息发送的时间     
            mailMessage.setSentDate(new Date());
            // MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象     
            Multipart mainPart = new MimeMultipart();
            // 创建一个包含HTML内容的MimeBodyPart     
            BodyPart html = new MimeBodyPart();
            // 设置HTML内容     
            html.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
            mainPart.addBodyPart(html);
            //附件
            if (mailInfo.getTempAttachmentStore() != null && mailInfo.getTempAttachmentStore().size() > 0) {
                List<String> attachFileNames = mailInfo.getTempAttachmentStore();
                for (String path : attachFileNames) {
                    html = new MimeBodyPart();
                    FileDataSource fds = new FileDataSource(path); //得到数据源    
                    html.setDataHandler(new DataHandler(fds)); //得到附件本身并至入BodyPart
                    //此处是为了解决附件中文名乱码的问题    
                    String fileName = MimeUtility.encodeText(fds.getName());
                    html.setFileName(fileName); //得到文件名同样至入BodyPart    
                    mainPart.addBodyPart(html);
                }
            }

            //图片
            Document document = Jsoup.parse(mailInfo.getContent());
            Elements elements = document.getElementsByTag("img");
            if (elements.size() > 0) {
                for (Element element : elements) {
                    String src = element.attr("src");
                    html = new MimeBodyPart();
                    DataSource fds = new FileDataSource(src);
                    html.setDataHandler(new DataHandler(fds));
                    String uuid = UUID.randomUUID().toString().replaceAll("-", "");
                    html.setHeader("Content-ID", "<" + uuid + ">");

                    // add it
                    mainPart.addBodyPart(html);
                }

            }

            // 将MiniMultipart对象设置为邮件内容     
            mailMessage.setContent(mainPart);
            // 发送邮件     
            Transport.send(mailMessage);
            return true;
        } catch (Exception e) {
            logger.error("发送失败！", e);
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        SenderMailVO senderMailVO = new SenderMailVO();
        senderMailVO.setReceiver("yanmingkun@for139.com");
        senderMailVO.setSender("yanmingkun@for139.com");
        senderMailVO.setContent("<H1>Hello</H1><img src=\"cid:image\">");
        senderMailVO.setTheme("这是发送的邮件");
        senderMailVO.setPassword("123456");
        List<String> list = new ArrayList<>();
        list.add("D:\\网状网发送给平台失败_20150923.xls");
        senderMailVO.setPictureId("D:\\无标题.png");
        senderMailVO.setTempAttachmentStore(list);
        SenderEmailClient.sendHtmlMail(senderMailVO);
    }
}
