package com.it.ymk.bubble.core.support;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.it.ymk.bubble.core.vo.ReturnVo;

/**
 * 基础增删改查
 *
 * @author yanmingkun
 * @date 2018-11-01 15:51
 */
public abstract class CrudController<T extends BaseEntity, S extends ICrudService<T, ID>, ID> extends BaseController {
    /**
     * 查询
     *
     * @param m
     * @param page
     * @param simple 只查少数字段
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/list")
    public ReturnVo list(T m, Integer pageSize, Integer page, Boolean simple, String sortField, String sortOrder) {

        return new ReturnVo();
    }
}
