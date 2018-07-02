package com.it.ymk.bubble.component.mail.entity;

import java.io.Serializable;
import java.util.List;

public class SenderMailVO implements Serializable {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 2091128786599029678L;

    /**
     * 邮件主题
     */
    private String            theme;
    /**
     * 邮件类型
     */
    private String            mailType;
    /**
     * 邮件地址
     */
    private String            sendAddress;
    /**
     * 邮件ID
     */
    private String            mailId;
    /**
     * 邮箱密码
     */
    private String            password;
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
    private String            tempContentStore;
    /**
     * 邮件附件保存的附件服务ID
     */
    private String            attachmentId;
    /**
     * 邮件附件临时地址
     */
    private List<String>      tempAttachmentStore;
    /**
     * 图片保存的附件 ID
     */
    private String            pictureId;
    /**
     * 图片保存的临时地址
     */
    private String            tempPictureStore;

    public String getMailId() {
        return mailId;
    }

    public void setMailId(String mailId) {
        this.mailId = mailId;
    }

    public String getMailType() {
        return mailType;
    }

    public void setMailType(String mailType) {
        this.mailType = mailType;
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

    public String getTempContentStore() {
        return tempContentStore;
    }

    public void setTempContentStore(String tempContentStore) {
        this.tempContentStore = tempContentStore;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public List<String> getTempAttachmentStore() {
        return tempAttachmentStore;
    }

    public void setTempAttachmentStore(List<String> tempAttachmentStore) {
        this.tempAttachmentStore = tempAttachmentStore;
    }

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getTempPictureStore() {
        return tempPictureStore;
    }

    public void setTempPictureStore(String tempPictureStore) {
        this.tempPictureStore = tempPictureStore;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }
}
