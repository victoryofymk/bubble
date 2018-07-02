package com.it.ymk.bubble.component.mail.client;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import javax.mail.search.*;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.it.ymk.bubble.component.mail.AuthenticatorGenerator;
import com.it.ymk.bubble.component.mail.constant.MailConstant;
import com.it.ymk.bubble.component.mail.entity.ReceiveHostType;
import com.it.ymk.bubble.component.mail.entity.ReceiveMailVO;
import com.it.ymk.bubble.core.utils.DateUtil;
import com.sun.mail.imap.IMAPFolder;
import com.sun.mail.pop3.POP3Folder;

/**
 * 读取邮箱接收邮件
 *
 * @author yanmingkun
 * @version 1.0
 * @create 2016-11-01 11:28
 **/
public class ReceiveEmailClient {
    /**
     * slf4j log
     */
    static Logger                      logger          = LoggerFactory.getLogger(ReceiveEmailClient.class);
    //存放邮件内容
    private static StringBuffer        contentBodyText = new StringBuffer();
    //存放邮件内容
    private static StringBuffer        contentHtmlText = new StringBuffer();

    //图片保存地址
    private static Map<String, String> imageMap        = new HashMap<>();

    /**
     *
     * @param username 用户名，邮箱账号
     * @param password 密码，登录密码
     * @param theme     主题关键字
     * @param authSender  匹配发件人
     * @param filter   过滤发件人
     * @param fileSize  附件大小
     * @return List<MailVO> 邮件列表
     * @throws Exception 异常
     */
    public static List<ReceiveMailVO> receiveMail(String username, String password, String theme, String authSender,
        String filter, int fileSize) throws Exception {
        List<ReceiveMailVO> mails;
        if (StringUtils.isEmpty(username)) {
            throw new Exception("用户名不能为空！");
        }
        if (StringUtils.isEmpty(password)) {
            throw new Exception("密码不能为空！");
        }
        String pop3Host = "";
        String imapHost = "";
        String protocol = "";
        String auth = "";
        Properties defaults = new Properties();
        defaults.put(MailConstant.MAIL_POP3_HOST, pop3Host);
        defaults.put(MailConstant.MAIL_IMAP_HOST, imapHost);
        defaults.put(MailConstant.MAIL_STORE_PROTOCOL, protocol); // 默认使用pop3收取邮件
        defaults.put(MailConstant.MAIL_POP3_AUTH, auth);
        if (protocol.equalsIgnoreCase(MailConstant.IMAP)) {
            mails = fetchInboxByImap(defaults,
                AuthenticatorGenerator.getAuthenticator(username, password), protocol, theme, authSender, filter,
                fileSize);
        }
        else if (protocol.equalsIgnoreCase(MailConstant.POP3)) {
            mails = fetchInboxByPop3(defaults,
                AuthenticatorGenerator.getAuthenticator(username, password), protocol);
        }
        else {
            throw new Exception("协议不正确！");
        }
        return mails;
    }

    /**
     * 收取邮件，多协议
     *
     * @param username 用户名，邮箱账号
     * @param password 密码，登录密码
     * @return List<MailVO> 邮件列表
     * @throws Exception 异常
     */
    public static List<ReceiveMailVO> receiveMail(String username, String password) throws Exception {
        List<ReceiveMailVO> mails;
        if (StringUtils.isEmpty(username)) {
            throw new Exception("用户名不能为空！");
        }
        if (StringUtils.isEmpty(password)) {
            throw new Exception("密码不能为空！");
        }
        /*String pop3Host = SystemConfig.getProperty(MAIL_POP3_HOST, DEFAULT_MAIL_POP3_HOST);
        String imapHost = SystemConfig.getProperty(MAIL_IMAP_HOST, DEFAULT_MAIL_IMAP_HOST);
        String protocol = SystemConfig.getProperty(MAIL_STORE_PROTOCOL, DEFAULT_STORE_PROTOCOL);
        String auth = SystemConfig.getProperty(MAIL_POP3_AUTH, DEFAULT_MAIL_POP3_AUTH);*/
        String pop3Host = "";
        String imapHost = "";
        String protocol = "";
        String auth = "";
        Properties defaults = new Properties();
        defaults.put(MailConstant.MAIL_POP3_HOST, pop3Host);
        defaults.put(MailConstant.MAIL_IMAP_HOST, imapHost);
        defaults.put(MailConstant.MAIL_STORE_PROTOCOL, protocol); // 默认使用pop3收取邮件
        defaults.put(MailConstant.MAIL_POP3_AUTH, auth);
        if (protocol.equalsIgnoreCase(MailConstant.IMAP)) {
            mails = fetchInboxByImap(defaults,
                AuthenticatorGenerator.getAuthenticator(username, password), protocol);
        }
        else if (protocol.equalsIgnoreCase(MailConstant.POP3)) {
            mails = fetchInboxByPop3(defaults,
                AuthenticatorGenerator.getAuthenticator(username, password), protocol);
        }
        else {
            throw new Exception("协议不正确！");
        }
        return mails;
    }

    /**
     * 从收件箱获取邮件，多协议
     *
     * @param props         参数设置
     * @param authenticator 认证
     * @param protocol      协议
     * @return List<MailVO> 邮件列表
     * @throws Exception 异常
     */
    private static List<ReceiveMailVO> fetchInboxByPop3(Properties props, Authenticator authenticator, String protocol)
        throws Exception {
        Message[] messages;
        List<ReceiveMailVO> mailList = new ArrayList<ReceiveMailVO>();
        Session session = Session.getInstance(props, authenticator);
        session.setDebug(true);
        Store store = null;
        POP3Folder folder = null;
        try {
            store = protocol == null || protocol.trim().length() == 0 ? session.getStore() : session.getStore(protocol);
            store.connect();
            folder = (POP3Folder) store.getFolder("INBOX");// 获取收件箱
            folder.open(Folder.READ_ONLY); // 以只读方式打开
            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);//false代表未读，true代表已读
            /**
             * Flag 类型列举如下
             * Flags.Flag.ANSWERED 邮件回复标记，标识邮件是否已回复。
             * Flags.Flag.DELETED 邮件删除标记，标识邮件是否需要删除。
             * Flags.Flag.DRAFT 草稿邮件标记，标识邮件是否为草稿。
             * Flags.Flag.FLAGGED 表示邮件是否为回收站中的邮件。
             * Flags.Flag.RECENT 新邮件标记，表示邮件是否为新邮件。
             * Flags.Flag.SEEN 邮件阅读标记，标识邮件是否已被阅读。
             * Flags.Flag.USER 底层系统是否支持用户自定义标记，只读。
             */
            messages = folder.search(ft);
            //            messages = folder.getMessages();
            for (Message message : messages) {
                if (!message.getFolder().isOpen()) {
                    message.getFolder().open(Folder.READ_ONLY);
                }
                String pop3Uid = folder.getUID(message);
                mailList.add(getMailVO(message, pop3Uid, null));

            }
            if (mailList.size() > 0) {
                folder.setFlags(messages, new Flags(Flags.Flag.SEEN), true);
            }
        } catch (Exception e) {
            logger.error("读取收件箱出错", e);
            throw new Exception(e);
        } finally {
            if (folder != null)
                folder.close(true);
            if (store != null)
                store.close();
        }
        return mailList;
    }

    /**
     * 设置已读状态
     *
     * @param username 用户名
     * @param password 密码
     * @param protocol 协议
     * @param list     邮件列表
     * @throws Exception 抛出异常
     */
    public void setReadFlag(String username, String password, String protocol, List<ReceiveMailVO> list)
        throws Exception {

        Session session = Session.getDefaultInstance(ReceiveHostType.MOBILE.getProperties(),
            AuthenticatorGenerator.getAuthenticator(username, password));
        session.setDebug(true);
        Store store = null;
        IMAPFolder folder = null;
        try {
            store = protocol == null || protocol.trim().length() == 0 ? session.getStore() : session.getStore(protocol);
            store.connect();
            folder = (IMAPFolder) store.getFolder(MailConstant.INBOX);// 获取收件箱
            folder.open(Folder.READ_WRITE);
            for (ReceiveMailVO mailVO : list) {
                Message message = folder.getMessageByUID(mailVO.getImapUid());
                Flags flags = message.getFlags();
                if (!flags.contains(Flags.Flag.SEEN)) {
                    message.setFlag(Flags.Flag.SEEN, true);
                }
            }
        } catch (Exception e) {
            logger.error("获取收件箱出错", e);
            throw new Exception(e);
        } finally {
            if (folder != null)
                folder.close(true);
            if (store != null)
                store.close();
        }

    }

    /**
     * 从收件箱获取邮件
     *
     * @param props         参数配置
     * @param authenticator 认证
     * @param protocol      协议，如pop3，imap
     * @return List<MailVO> 返回邮件列表
     * @throws Exception 异常
     */
    private static List<ReceiveMailVO> fetchInboxByImap(Properties props, Authenticator authenticator, String protocol)
        throws Exception {
        Message[] messages;
        List<ReceiveMailVO> mailList = new ArrayList<ReceiveMailVO>();
        Session session = Session.getInstance(props, authenticator);
        session.setDebug(true);
        Store store = null;
        IMAPFolder folder = null;
        try {
            store = protocol == null || protocol.trim().length() == 0 ? session.getStore() : session.getStore(protocol);
            store.connect();
            folder = (IMAPFolder) store.getFolder(MailConstant.INBOX);// 获取收件箱
            folder.open(Folder.READ_WRITE);
            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);//false代表未读，true代表已读
            /**
             * Flag 类型列举如下
             * Flags.Flag.ANSWERED 邮件回复标记，标识邮件是否已回复。
             * Flags.Flag.DELETED 邮件删除标记，标识邮件是否需要删除。
             * Flags.Flag.DRAFT 草稿邮件标记，标识邮件是否为草稿。
             * Flags.Flag.FLAGGED 表示邮件是否为回收站中的邮件。
             * Flags.Flag.RECENT 新邮件标记，表示邮件是否为新邮件。
             * Flags.Flag.SEEN 邮件阅读标记，标识邮件是否已被阅读。
             * Flags.Flag.USER 底层系统是否支持用户自定义标记，只读。
             */
            messages = folder.search(ft);
            for (Message message : messages) {
                if (!message.getFolder().isOpen()) {
                    message.getFolder().open(Folder.READ_WRITE);
                }
                long uid = folder.getUID(message);
                mailList.add(getMailVO(message, null, uid));
            }
            if (mailList.size() > 0) {
                folder.setFlags(messages, new Flags(Flags.Flag.SEEN), true);
            }

        } catch (Exception e) {
            logger.error("获取收件箱出错", e);
            throw new Exception(e);
        } finally {
            if (folder != null)
                folder.close(true);
            if (store != null)
                store.close();
        }
        return mailList;
    }

    /**
     * 从收件箱获取邮件
     *
     * @param props 参数配置
     * @param authenticator 认证
     * @param protocol
     *            协议，如pop3，imap
     * @return List<MailVO> 返回邮件列表
     * @throws Exception 异常
     */
    public static List<ReceiveMailVO> fetchInboxByImap(Properties props, Authenticator authenticator, String protocol,
        String theme, String auth, String filter, int fileSize)
        throws Exception {
        Message[] messages;
        List<ReceiveMailVO> mailList = new ArrayList<ReceiveMailVO>();
        Session session = Session.getInstance(props, authenticator);
        session.setDebug(false);
        Store store = null;
        IMAPFolder folder = null;
        try {
            store = protocol == null || protocol.trim().length() == 0 ? session.getStore() : session.getStore(protocol);
            store.connect();
            folder = (IMAPFolder) store.getFolder("INBOX");// 获取收件箱
            folder.open(Folder.READ_WRITE);
            //搜索条件
            FlagTerm ft = new FlagTerm(new Flags(Flags.Flag.SEEN), false);//false代表未读，true代表已读
            List<SearchTerm> list = new ArrayList<>();
            if (StringUtils.isNotEmpty(theme)) {
                SearchTerm themeTerm = new SubjectTerm(theme);
                list.add(themeTerm);
            }
            if (StringUtils.isNotEmpty(auth)) {
                String[] authArray = filter.split(";");
                //发件人
                for (int i = 0; i < authArray.length; i++) {
                    SearchTerm notTerm = new FromStringTerm(authArray[i]);
                    list.add(notTerm);
                }
            }
            if (StringUtils.isNotEmpty(filter)) {
                String[] stringArray = filter.split(";");
                //发件人过滤
                for (int i = 0; i < stringArray.length; i++) {
                    SearchTerm notTerm = new NotTerm(new FromStringTerm(stringArray[i]));
                    list.add(notTerm);
                }
            }
            //文件大小
            if (fileSize != 0) {
                SearchTerm intComparisonTerm = new SizeTerm(IntegerComparisonTerm.LE, fileSize);
                list.add(intComparisonTerm);
            }
            list.add(ft);
            SearchTerm[] search = list.toArray(new SearchTerm[list.size()]);
            SearchTerm andTerm = new AndTerm(search);

            /**
             * Flag 类型列举如下
             * Flags.Flag.ANSWERED 邮件回复标记，标识邮件是否已回复。
             * Flags.Flag.DELETED 邮件删除标记，标识邮件是否需要删除。
             * Flags.Flag.DRAFT 草稿邮件标记，标识邮件是否为草稿。
             * Flags.Flag.FLAGGED 表示邮件是否为回收站中的邮件。
             * Flags.Flag.RECENT 新邮件标记，表示邮件是否为新邮件。
             * Flags.Flag.SEEN 邮件阅读标记，标识邮件是否已被阅读。
             * Flags.Flag.USER 底层系统是否支持用户自定义标记，只读。
             */
            messages = folder.search(andTerm);
            for (Message message : messages) {
                if (!message.getFolder().isOpen()) {
                    message.getFolder().open(Folder.READ_WRITE);
                }
                long uid = folder.getUID(message);
                mailList.add(getMailVO(message, null, uid));
            }
            if (mailList.size() > 0) {
                folder.setFlags(messages, new Flags(Flags.Flag.SEEN), true);
            }

        } catch (Exception e) {
            logger.error("获取收件箱出错", e);
            throw new Exception(e);
        } finally {
            if (folder != null)
                folder.close(true);
            if (store != null)
                store.close();
        }
        return mailList;
    }

    /**
     * 解析邮件生成MailVO
     *
     * @param message 原始消息
     *                原始邮件信息
     * @param uid
     * @return MailVO 邮件实体
     * @throws Exception 异常
     */
    private static ReceiveMailVO getMailVO(Message message, String pop3Uid, Long uid) throws Exception {
        ReceiveMailVO mailVO = new ReceiveMailVO();
        if (StringUtils.isNotEmpty(pop3Uid)) {
            mailVO.setPop3Uid(pop3Uid);
        }
        else {
            mailVO.setImapUid(uid);
        }

        mailVO.setTheme(getSubject((MimeMessage) message));
        mailVO.setSender(getFrom((MimeMessage) message));
        // 获取邮件内容
        contentBodyText.setLength(0);
        contentHtmlText.setLength(0);
        getMailContent(message);
        String htmlContent = StringUtils.isEmpty(contentHtmlText.toString()) ? contentBodyText.toString()
            : contentHtmlText.toString();
        Set<Entry<String, String>> set = imageMap.entrySet();
        Iterator<Entry<String, String>> it = set.iterator();
        StringBuffer sb = new StringBuffer();

        while (it.hasNext()) {
            Entry<String, String> entry = it.next();
            String cid = entry.getKey();
            String src = entry.getValue();
            htmlContent = htmlContent.replace(cid, src);
            sb.append(src + ";");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.lastIndexOf(";"));
        }
        mailVO.setTempPictureStore(sb.toString());
        mailVO.setContent(htmlContent);
        mailVO.setSendDate(getSentDate((MimeMessage) message));
        mailVO.setReceivedDate(getReceivedDate((MimeMessage) message));
        mailVO.setReceiver(getMailAddress(MailConstant.TO, (MimeMessage) message));
        mailVO.setCc(getMailAddress(MailConstant.CC, (MimeMessage) message));
        List<String> fileStore = saveAttachment(message);
        mailVO.setTempAttachmentStore(fileStore);
        //        mailVO.setAttachmentId(MailUtil.uploadFile(fileStore));
        return mailVO;

    }

    /**
     * 获得发件人的地址和姓名
     *
     * @param message 原始消息
     *                原始邮件信息
     * @return String 发件人地址
     * @throws Exception 异常
     */
    private static String getFrom(MimeMessage message) throws Exception {
        InternetAddress address[] = (InternetAddress[]) message.getFrom();
        String from = address[0].getAddress();
        if (from == null)
            from = "";
        return from;
    }

    /**
     * 获得邮件的收件人，抄送，和密送的地址和姓名，根据所传递的参数的不同 "to"----收件人 "cc"---抄送人地址 "bcc"---密送人地址
     *
     * @param type    地址类型
     * @param message 原始消息
     *                原始邮件信息
     * @return String 地址
     * @throws Exception 异常
     */
    private static String getMailAddress(String type, MimeMessage message) throws Exception {
        String mailAddress = "";
        String addType = type.toUpperCase();
        InternetAddress[] address;
        if (addType.equals(MailConstant.TO) || addType.equals(MailConstant.CC) || addType.equals(MailConstant.BCC)) {
            if (addType.equals(MailConstant.TO)) {
                address = (InternetAddress[]) message.getRecipients(Message.RecipientType.TO);
            }
            else if (addType.equals(MailConstant.CC)) {
                address = (InternetAddress[]) message.getRecipients(Message.RecipientType.CC);
            }
            else {
                address = (InternetAddress[]) message.getRecipients(Message.RecipientType.BCC);
            }
            if (address != null) {
                for (InternetAddress internetAddress : address) {
                    String email = internetAddress.getAddress();
                    if (email == null)
                        email = "";
                    else {
                        email = MimeUtility.decodeText(email);
                    }
                    mailAddress += ";" + email;
                }
                mailAddress = mailAddress.substring(1);
            }
        }
        else {
            throw new Exception("Error mailAddress type!");
        }
        return mailAddress;
    }

    /**
     * 获得邮件主题
     *
     * @param message 原始邮件信息
     * @return String 主题
     * @throws Exception 异常
     */
    private static String getSubject(MimeMessage message) throws Exception {
        String subject;
        try {
            subject = MimeUtility.decodeText(message.getSubject());
        } catch (Exception e) {
            logger.error("Error get subject!", e);
            throw new Exception(e);
        }
        return subject;
    }

    /**
     * 获得邮件发送日期
     *
     * @param message 原始消息
     * @return String 发送日期
     * @throws Exception 异常
     */
    private static Date getSentDate(MimeMessage message) throws Exception {
        Date sentDate = message.getSentDate();
        return sentDate;
    }

    /**
     * 获得邮件接收日期
     *
     * @param message 原始消息
     * @return String 接收日期
     * @throws Exception 异常
     */
    private static Date getReceivedDate(MimeMessage message) throws Exception {
        Date receivedDate = message.getReceivedDate();
        return receivedDate;
    }

    /**
     * 获得邮件正文内容
     *
     * @return String 邮件正文
     */
    private static String getBodyText() {
        return contentBodyText.toString();
    }

    /**
     * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件 主要是根据MimeType类型的不同执行不同的操作，一步一步的解析
     *
     * @param part 邮件协议
     * @throws Exception 异常
     */
    private static void getMailContent(Part part) throws Exception {
        String contentType = part.getContentType();
        int nameIndex = contentType.indexOf("name");
        if (part.isMimeType("text/plain") && nameIndex == -1) {
            contentBodyText.append((String) part.getContent());
        }
        if (part.isMimeType("text/html") && nameIndex == -1) {
            contentHtmlText.append((String) part.getContent());
        }
        else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int counts = multipart.getCount();
            for (int i = 0; i < counts; i++) {
                getMailContent(multipart.getBodyPart(i));
            }
        }
        else if (part.isMimeType("message/rfc822")) {
            getMailContent((Part) part.getContent());
        }
        else if (contentType.contains("image/jpeg")) {
            String id = "cid:" + part.getHeader("Content-ID")[0].replace("<", "").replace(">", "");
            String root = MailConstant.TEMP_PIC_PRE + DateUtil.dateToDateString(new Date(), DateUtil.YYYY_MM_DD_EN)
                          + File.separator;
            String fileSrc = "image" + System.currentTimeMillis() + ".jpg";

            File relaFile = new File(root + fileSrc);
            File image;
            if (!relaFile.getParentFile().exists()) {
                relaFile.getParentFile().mkdirs();
            }
            if (!relaFile.getParentFile().exists()) {
                image = new File(relaFile.getAbsolutePath());
                if (!image.getParentFile().exists()) {
                    image.getParentFile().mkdirs();
                }
            }
            else {
                image = relaFile;
            }
            imageMap.put(id, root + fileSrc);
            if (!image.exists()) {
                image.createNewFile();
            }
            DataOutputStream output = null;
            try {
                output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(image)));
                com.sun.mail.util.BASE64DecoderStream test = (com.sun.mail.util.BASE64DecoderStream) part.getContent();
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = test.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
            } catch (Exception e) {
                logger.error("解析邮件内容出错！" + e);
                throw new Exception(e);
            } finally {
                if (output != null) {
                    output.close();
                }
            }

        }
    }

    /**
     * 获得此邮件的Message-ID
     *
     * @param message 原始消息
     * @return String 消息messageId
     * @throws MessagingException 异常
     */
    private static String getMessageId(MimeMessage message) throws MessagingException {
        return message.getMessageID();
    }

    /**
     * 判断此邮件是否已读，如果未读返回返回false,反之返回true
     *
     * @param message 原始消息
     * @return boolean true or false
     * @throws MessagingException 异常
     */
    public boolean isNew(MimeMessage message) throws MessagingException {
        boolean isNew = false;
        Flags flags = message.getFlags();
        Flags.Flag[] flag = flags.getSystemFlags();
        for (int i = 0; i < flag.length; i++) {
            if (flag[i] == Flags.Flag.SEEN) {
                isNew = true;
                break;
            }
        }
        return isNew;
    }

    /**
     * 判断此邮件是否包含附件
     *
     * @param part 邮件协议
     * @return boolean true or false
     * @throws Exception 异常
     */
    public boolean isContainAttach(Part part) throws Exception {
        boolean attachFlag = false;
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart bodyPart = mp.getBodyPart(i);
                String disposition = bodyPart.getDisposition();
                if ((disposition != null)
                    && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE))))
                    attachFlag = true;
                else if (bodyPart.isMimeType("multipart/*")) {
                    attachFlag = isContainAttach(bodyPart);
                }
                else {
                    String contentType = bodyPart.getContentType();
                    if (contentType.toLowerCase().contains("application"))
                        attachFlag = true;
                    if (contentType.toLowerCase().contains("name"))
                        attachFlag = true;
                }
            }
        }
        else if (part.isMimeType("message/rfc822")) {
            attachFlag = isContainAttach((Part) part.getContent());
        }
        return attachFlag;
    }

    /**
     * 保存附件
     *
     * @param part 邮件协议
     * @return String 邮件附件地址
     * @throws Exception 异常
     */
    private static List<String> saveAttachment(Part part) throws Exception {
        String fileName;
        List<String> tempFileStore = new ArrayList<String>();
        String storeDir = (MailConstant.TEMP_FILE_PRE
                           + DateUtil.dateToDateString(new Date(), DateUtil.YYYYMMDDHHMMSS_EN));
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart bodyPart = mp.getBodyPart(i);
                String disposition = bodyPart.getDisposition();
                if ((disposition != null)
                    && ((disposition.equals(Part.ATTACHMENT)) || (disposition.equals(Part.INLINE)))) {
                    fileName = bodyPart.getFileName();
                    fileName = MimeUtility.decodeText(fileName);

                    String fileStore = saveFile(storeDir, fileName, bodyPart.getInputStream());
                    tempFileStore.add(fileStore);
                }
                else if (bodyPart.isMimeType("multipart/*")) {
                    saveAttachment(bodyPart);
                }
                else {
                    fileName = bodyPart.getFileName();
                    if (StringUtils.isNotEmpty(fileName)) {
                        fileName = MimeUtility.decodeText(fileName);
                        String fileStore = saveFile(storeDir, fileName, bodyPart.getInputStream());
                        tempFileStore.add(fileStore);
                    }
                }
            }
        }
        else if (part.isMimeType("message/rfc822")) {
            saveAttachment((Part) part.getContent());
        }
        return tempFileStore;
    }

    /**
     * 真正的保存附件到指定目录里
     *
     * @param storeDir 地址
     * @param fileName 文件名
     * @param in       输入流
     * @return String 保存的地址
     * @throws Exception 异常
     */
    private static String saveFile(String storeDir, String fileName, InputStream in) throws Exception {

        File relaFile = new File(storeDir + File.separator + fileName);
        logger.debug("相对路径：" + storeDir + "绝对路径" + relaFile.getAbsolutePath());
        if (!relaFile.getParentFile().exists()) {
            relaFile.getParentFile().mkdirs();
        }
        File storeFile;
        if (!relaFile.getParentFile().exists()) {
            storeFile = new File(relaFile.getAbsolutePath());
            if (!storeFile.getParentFile().exists()) {
                storeFile.getParentFile().mkdirs();
            }

        }
        else {
            storeFile = relaFile;
        }
        logger.debug("实际路径：" + storeFile.getAbsolutePath());
        if (!storeFile.exists()) {
            storeFile.createNewFile();
        }

        BufferedOutputStream bos = null;
        BufferedInputStream bis = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(storeFile));
            bis = new BufferedInputStream(in);
            int c;
            while ((c = bis.read()) != -1) {
                bos.write(c);
                bos.flush();
            }
        } catch (Exception e) {
            logger.error("文件保存失败!", e);
            throw new Exception(e);
        } finally {
            bos.close();
            bis.close();
        }
        return storeFile.getAbsolutePath();
    }
}
