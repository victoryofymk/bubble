package com.it.ymk.bubble.component.schedule.dao;

import java.util.List;

import com.it.ymk.bubble.component.schedule.entity.JobAndTrigger;
import com.it.ymk.bubble.core.mybatis.MyBatisRepository;

/**
 * @author yanmingkun
 * @date 2018-01-19 09:43
 */
@MyBatisRepository
public interface JobAndTriggerMapper {
    List<JobAndTrigger> getJobAndTriggerDetails();
}
