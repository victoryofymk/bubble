package com.it.ymk.bubble.web.pojo;

import java.io.Serializable;

/**
*@Title Item.java
*@description 商品
*@time 2016年9月22日 下午2:14:51
*@author yanmingkun
*@version 1.0
**/
public class Item implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -1908393659164670070L;
    /**
     * 商品ID
     */
    private String            ID;
    /**
     * 商品名称
     */
    private String            name;
    /**
     * 商品地址
     */
    private String            src;
    /**
     * 商品价格
     */
    private String            pritice;

    /**
     * @return the iD
     */
    public String getID() {
        return ID;
    }

    /**
     * @param iD
     *            the iD to set
     */
    public void setID(String iD) {
        ID = iD;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the pritice
     */
    public String getPritice() {
        return pritice;
    }

    /**
     * @param pritice
     *            the pritice to set
     */
    public void setPritice(String pritice) {
        this.pritice = pritice;
    }

    /**
     * @return the src
     */
    public String getSrc() {
        return src;
    }

    /**
     * @param src
     *            the src to set
     */
    public void setSrc(String src) {
        this.src = src;
    }

}
