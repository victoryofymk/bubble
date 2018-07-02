package com.it.ymk.bubble.component.mail.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

/**
 * 
 * @description 邮件实体类
 * @author yanmingkun
 * @date 创建时间：2016年11月2日 上午10:12:58 
 * @version 1.0
 */
public class ReceiveMailVO implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 8962822629556384152L;
    /**
     *  返回的邮件信息
     */
    private MimeMessage       mimeMessage;
    /**
     * imap邮件uid
     */
    private long              imapUid;
    /**
     * pop3邮件uid
     */
    private String            pop3Uid;
    /**
     * 邮件主题
     */
    private String            theme;
    /**
     * 邮件地址
     */
    private String            receiveAddress;
    /**
     * 发件人
     */
    private String            sender;
    /**
     * 收件人
     */
    private String            receiver;
    /**
     * 抄送人
     */
    private String            cc;
    /**
     * 邮件内容
     */
    private String            content;
    /**
     * 保存的附件ID
     */
    private String            contentId;
    /**
     * 邮件内容临时地址
     */
    private List<String>      tempAttachmentStore;
    /**
     * 邮件附件保存的附件服务ID
     */
    private String            attachmentId;
    /**
     * 邮件附件临时地址
     */
    private String            tempContentStore;
    /**
     * 图片保存的附件 ID
     */
    private String            pictureId;
    /**
     * 图片保存的临时地址
     */
    private String            tempPictureStore;
    /**
     * 邮件发送日期
     */
    private Date              sendDate;
    /**
     * 邮件接收日期
     */
    private Date              receivedDate;

    public MimeMessage getMimeMessage() {
        return mimeMessage;
    }

    public void setMimeMessage(MimeMessage mimeMessage) {
        this.mimeMessage = mimeMessage;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public Date getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(Date receivedDate) {
        this.receivedDate = receivedDate;
    }

    public List<String> getTempAttachmentStore() {
        return tempAttachmentStore;
    }

    public void setTempAttachmentStore(List<String> tempAttachmentStore) {
        this.tempAttachmentStore = tempAttachmentStore;
    }

    public String getTempContentStore() {
        return tempContentStore;
    }

    public void setTempContentStore(String tempContentStore) {
        this.tempContentStore = tempContentStore;
    }

    public String getTempPictureStore() {
        return tempPictureStore;
    }

    public void setTempPictureStore(String tempPictureStore) {
        this.tempPictureStore = tempPictureStore;
    }

    public String getPop3Uid() {
        return pop3Uid;
    }

    public void setPop3Uid(String pop3Uid) {
        this.pop3Uid = pop3Uid;
    }

    public long getImapUid() {
        return imapUid;
    }

    public void setImapUid(long imapUid) {
        this.imapUid = imapUid;
    }

    public String getReceiveAddress() {
        return receiveAddress;
    }

    public void setReceiveAddress(String receiveAddress) {
        this.receiveAddress = receiveAddress;
    }
}
