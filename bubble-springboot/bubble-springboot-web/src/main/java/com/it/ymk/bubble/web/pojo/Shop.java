package com.it.ymk.bubble.web.pojo;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @Title Shop.java
 * @description 商店pojo
 * @time 2016年9月22日 下午2:15:16
 * @author yanmingkun
 * @version 1.0
 *
 */
public class Shop implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 8660191828234644739L;
    /**
     *
     */
    private String            ID;
    /**
     * 商店ID
     */
    private String            name;
    /**
     * 商店名称
     */
    private String            src;
    /**
     * 所有商品
     */
    private List<Item>        listItem;

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

    /**
     * @return the listItem
     */
    public List<Item> getListItem() {
        return listItem;
    }

    /**
     * @param listItem
     *            the listItem to set
     */
    public void setListItem(List<Item> listItem) {
        this.listItem = listItem;
    }
}
