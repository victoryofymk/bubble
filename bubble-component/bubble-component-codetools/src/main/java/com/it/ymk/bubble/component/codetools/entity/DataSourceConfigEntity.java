package com.it.ymk.bubble.component.codetools.entity;

import com.it.ymk.bubble.component.codetools.config.DataBaseConfig;

public class DataSourceConfigEntity extends DataBaseConfig {
    private int    dcId;
    private String backUser;

    public int getDcId() {
        return dcId;
    }

    public void setDcId(int dcId) {
        this.dcId = dcId;
    }

    public String getBackUser() {
        return backUser;
    }

    public void setBackUser(String backUser) {
        this.backUser = backUser;
    }

}
