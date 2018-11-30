package com.it.ymk.bubble.component.schedule.config;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.it.ymk.bubble.component.schedule.entity.QuartzLog;
import com.it.ymk.bubble.component.schedule.service.QuartzLogService;
import com.it.ymk.bubble.core.utils.BeanLocator;

/**
 * 执行监听器,监控job运行
 * 
 * @author yanmingkun
 * @date 2018-11-30 16:08
 */
public class MonitorTriggerListener implements TriggerListener {
    private final static Logger    logger   = LoggerFactory.getLogger(MonitorTriggerListener.class);
    private ThreadLocal<QuartzLog> localLog = new ThreadLocal<QuartzLog>();

    /**
     * @see org.quartz.TriggerListener#getName()
     */
    @Override
    public String getName() {
        return "MonitorTriggerListener";
    }

    /**
     * @see org.quartz.TriggerListener
     */
    @Override
    public void triggerComplete(Trigger arg0, JobExecutionContext jec, Trigger.CompletedExecutionInstruction arg2) {
        try {
            logger.info("记录job结束时间");
            QuartzLog quartzLog = localLog.get();
            if (quartzLog == null) {
                return;
            }
            //            quartzLog.setExeTime(jec.getJobRunTime());
            //            getService().update(quartzLog);
        } catch (Exception e) {
            logger.error("记录job结束时间异常", e);
        } catch (Throwable e) {
            logger.error("记录job结束时间出错", e);
        }
    }

    /**
     * @see org.quartz.TriggerListener#triggerFired(org.quartz.Trigger, org.quartz.JobExecutionContext)
     */
    @Override
    public void triggerFired(Trigger arg0, JobExecutionContext jec) {
        try {
            logger.info("记录job开始时间");
            JobDetail jobDetail = jec.getJobDetail();
            //            System.out.println(localLog.get());
            //            QuartzLog quartzLog = getService().insert(jobDetail.getName());
            //            localLog.set(quartzLog);
            //            System.out.println(quartzLog);
        } catch (Exception e) {
            logger.error("记录job开始时间异常", e);
        } catch (Throwable e) {
            logger.error("记录job开始时间出错", e);
        }
    }

    /**
     * @see org.quartz.TriggerListener#triggerMisfired(org.quartz.Trigger)
     */
    @Override
    public void triggerMisfired(Trigger arg0) {
    }

    /**
     * @see org.quartz.TriggerListener#vetoJobExecution(org.quartz.Trigger, org.quartz.JobExecutionContext)
     */
    @Override
    public boolean vetoJobExecution(Trigger arg0, JobExecutionContext arg1) {
        return false;
    }

    private QuartzLogService getService() {
        return (QuartzLogService) BeanLocator.getBeanInstance("quartzLogService");
    }
}
