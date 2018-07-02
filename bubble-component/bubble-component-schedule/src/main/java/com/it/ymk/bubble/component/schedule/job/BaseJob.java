package com.it.ymk.bubble.component.schedule.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author yanmingkun
 * @date 2018-01-18 20:45
 */
public interface BaseJob extends Job {
    public void execute(JobExecutionContext context) throws JobExecutionException;
}
