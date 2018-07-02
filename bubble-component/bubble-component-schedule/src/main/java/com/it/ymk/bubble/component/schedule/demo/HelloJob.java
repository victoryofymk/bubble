package com.it.ymk.bubble.component.schedule.demo;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.it.ymk.bubble.component.schedule.job.BaseJob;

/**
 * @author yanmingkun
 * @date 2018-01-18 20:47
 */
public class HelloJob implements BaseJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloJob.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("Hello Job开始执行{}", new Date());
    }
}
