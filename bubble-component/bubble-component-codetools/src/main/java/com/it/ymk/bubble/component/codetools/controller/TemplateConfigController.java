package com.it.ymk.bubble.component.codetools.controller;

import org.durcframework.core.controller.CrudController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ymk.bubble.component.codetools.entity.TemplateConfigEntity;
import com.it.ymk.bubble.component.codetools.service.TemplateConfigService;

@Controller
public class TemplateConfigController extends CrudController<TemplateConfigEntity, TemplateConfigService> {

    @RequestMapping("/addTemplate.do")
    public @ResponseBody
        Object addTemplate(TemplateConfigEntity templateConfig) {
        return this.save(templateConfig);
    }

    @RequestMapping("/updateTemplate.do")
    public @ResponseBody
        Object updateTemplate(TemplateConfigEntity templateConfig) {
        return this.update(templateConfig);
    }

    @RequestMapping("/delTemplate.do")
    public @ResponseBody
        Object delTemplate(TemplateConfigEntity templateConfig) {
        return this.delete(templateConfig);
    }
}
