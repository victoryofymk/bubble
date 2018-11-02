package com.it.ymk.bubble.component.schedule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.it.ymk.bubble.component.schedule.dao.JobAndTriggerMapper;
import com.it.ymk.bubble.component.schedule.entity.JobAndTrigger;
import com.it.ymk.bubble.component.schedule.service.IJobAndTriggerService;

/**
 * @author yanmingkun
 * @date 2018-01-19 09:49
 */
@Service
public class JobAndTriggerImpl implements IJobAndTriggerService {

    @Autowired
    private JobAndTriggerMapper jobAndTriggerMapper;

    public PageInfo<JobAndTrigger> getJobAndTriggerDetails(Page<JobAndTrigger> page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize(), page.getOrderBy());
        List<JobAndTrigger> list = jobAndTriggerMapper.getJobAndTriggerDetails();
        PageInfo<JobAndTrigger> pagedata = new PageInfo<JobAndTrigger>(list);
        return pagedata;
    }

}
