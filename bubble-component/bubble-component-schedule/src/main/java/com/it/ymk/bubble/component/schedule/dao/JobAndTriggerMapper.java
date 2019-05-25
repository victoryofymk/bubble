package com.it.ymk.bubble.component.schedule.dao;

import com.it.ymk.bubble.component.schedule.entity.JobAndTrigger;

import java.util.List;

/**
 * @author yanmingkun
 * @date 2018-01-19 09:43
 */
public interface JobAndTriggerMapper {
    List<JobAndTrigger> getJobAndTriggerDetails();
}
