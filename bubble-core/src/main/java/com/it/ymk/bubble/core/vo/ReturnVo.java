package com.it.ymk.bubble.core.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yanmingkun
 * @date 2018-11-01 17:43
 */
public class ReturnVo implements Serializable {
    private static final long     serialVersionUID = 5532506292542561046L;

    /**
     * 返回的信息
     */
    protected String              message;
    /**
     * 执行是否成功
     */
    protected Boolean             success;
    /**
     * 异常信息
     */
    protected Integer             errNo;
    /**
     * 返回的对象
     */
    protected Map<String, Object> data             = new HashMap<String, Object>();

    public ReturnVo() {
    }

    public ReturnVo(Boolean success) {
        this.success = success;
    }

    public ReturnVo(Integer errNo, String message) {
        this.errNo = errNo;
        this.message = message;
        this.success = false;
    }

    /**
     * 添加数据
     *
     * @param key
     * @param value
     */
    public ReturnVo addData(String key, Object value) {
        data.put(key, value);
        return this;
    }

    /**
     * 添加
     *
     * @param key
     * @param value
     */
    public ReturnVo setRows(String key, Object value) {
        return addData("rows", value);
    }

    /**
     * 添加
     *
     * @param key
     * @param value
     */
    public ReturnVo setPage(String key, Object value) {
        return addData("total", value);
    }

}
