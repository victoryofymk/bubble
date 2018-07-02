package com.it.ymk.component.httpclient.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yanmingkun
 * @date 2017-12-12 18:15
 */
public class HttpResponseVO implements Serializable {

    private static final long serialVersionUID = -134179494624109383L;

    private Integer           code;
    private String            msg;
    private String            data;
    private Date              beginTime;
    private Date              endTime;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "HttpResponseVO{" +
               "code='" + code + '\'' +
               ", msg='" + msg + '\'' +
               ", data='" + data + '\'' +
               '}';
    }
}
