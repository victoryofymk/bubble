package com.it.ymk.bubble.component.schedule.service;

import com.github.pagehelper.PageInfo;
import com.it.ymk.bubble.component.schedule.entity.JobAndTrigger;

/**
 * @author yanmingkun
 * @date 2018-01-19 09:48
 */
public interface IJobAndTriggerService {
    public PageInfo<JobAndTrigger> getJobAndTriggerDetails(int pageNum, int pageSize);
}
