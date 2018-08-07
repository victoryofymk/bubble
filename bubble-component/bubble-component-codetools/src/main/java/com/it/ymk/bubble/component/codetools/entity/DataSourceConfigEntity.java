package com.it.ymk.bubble.component.codetools.entity;

import org.durcframework.core.expression.annotation.ValueField;

import com.it.ymk.bubble.component.codetools.config.DataBaseConfig;

public class DataSourceConfigEntity extends DataBaseConfig {
    private String name;
    private String description;
    private String driverClass;
    private String jdbcUrl;
    private String username;
    private String password;

    public void setName(String name) {
        this.name = name;
    }

    @ValueField(column = "name")
    public String getName() {
        return this.name;
    }

    @Override
    public void setDriverClass(String driverClass) {
        this.driverClass = driverClass;
    }

    @Override
    @ValueField(column = "driver_class")
    public String getDriverClass() {
        return this.driverClass;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    @Override
    @ValueField(column = "jdbc_url")
    public String getJdbcUrl() {
        return this.jdbcUrl;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    @ValueField(column = "username")
    public String getUsername() {
        return this.username;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    @ValueField(column = "password")
    public String getPassword() {
        return this.password;
    }

    public String getDescription() {
        return description;
    }

    @ValueField(column = "description")
    public void setDescription(String description) {
        this.description = description;
    }
}
