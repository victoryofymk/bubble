package com.it.ymk.bubble.component.codetools.controller;

import org.durcframework.core.MessageResult;
import org.durcframework.core.controller.CrudController;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ymk.bubble.component.codetools.config.DBConnect;
import com.it.ymk.bubble.component.codetools.entity.DataSourceConfigEntity;
import com.it.ymk.bubble.component.codetools.service.DataSourceConfigService;

@Controller
public class DataSourceConfigController extends CrudController<DataSourceConfigEntity, DataSourceConfigService> {

    @RequestMapping("/addDataSource.ajax")
    public @ResponseBody
        MessageResult addDataSource(DataSourceConfigEntity dataSourceConfigEntity) {
        return this.save(dataSourceConfigEntity);
    }

    @RequestMapping("/updateDataSource.ajax")
    public @ResponseBody
        MessageResult updateDataSource(DataSourceConfigEntity dataSourceConfigEntity) {
        return this.update(dataSourceConfigEntity);
    }

    @RequestMapping("/delDataSource.ajax")
    public @ResponseBody
        MessageResult delDataSource(DataSourceConfigEntity dataSourceConfigEntity) {
        return this.delete(dataSourceConfigEntity);
    }

    @RequestMapping("/connectionTest.ajax")
    public @ResponseBody
        MessageResult connectionTest(DataSourceConfigEntity dataSourceConfigEntity) {
        String connectInfo = DBConnect.testConnection(dataSourceConfigEntity);

        if (StringUtils.hasText(connectInfo)) {
            return error(connectInfo);
        }

        return success();

    }

}
