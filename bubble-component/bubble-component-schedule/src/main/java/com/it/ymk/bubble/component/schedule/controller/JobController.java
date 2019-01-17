package com.it.ymk.bubble.component.schedule.controller;

import static org.quartz.JobBuilder.newJob;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.it.ymk.bubble.component.schedule.entity.JobAndTrigger;
import com.it.ymk.bubble.component.schedule.job.BaseJob;
import com.it.ymk.bubble.component.schedule.service.IJobAndTriggerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * @author yanmingkun
 * @date 2018-01-18 20:52
 */
@Api("Quartz定时任务管理")
@RestController
@RequestMapping(value = "/job")
public class JobController<T> {
    @Autowired
    private IJobAndTriggerService iJobAndTriggerService;

    @Autowired
    @Qualifier("Scheduler")
    private Scheduler             scheduler;

    /**
     * 新增一个定时任务
     *
     * @param jobClassName
     * @param jobGroupName
     * @param cronExpression
     * @throws Exception
     */
    @ApiOperation("新增一个定时任务")
    @ApiImplicitParams({ @ApiImplicitParam(name = "jobClassName", value = "任务class名称", dataType = "String"),
                         @ApiImplicitParam(name = "jobGroupName", value = "任务分组名称", dataType = "String"),
                         @ApiImplicitParam(name = "cronExpression", value = "任务时间表达式  ", dataType = "String") })
    @PostMapping(value = "/addSingleJob")
    public void addSingleJob(@RequestParam(value = "jobClassName") String jobClassName,
        @RequestParam(value = "jobGroupName") String jobGroupName,
        @RequestParam(value = "cronExpression") String cronExpression) throws Exception {
        addJob(jobClassName, jobGroupName, cronExpression);
    }

    /**
     * 批量新增定时任务
     *
     * @param list
     * @throws Exception
     */
    @ApiOperation("批量新增定时任务")
    @ApiImplicitParam(name = "list", value = "任务数组", dataType = "JobAndTrigger")
    @PostMapping(value = "/addBatchJob")
    public void addBatchJob(List<JobAndTrigger> list) throws Exception {
        //        addJob(jobClassName, jobGroupName, cronExpression);
    }

    /**
     * 删除一个定时任务
     *
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @ApiOperation("删除一个定时任务")
    @ApiImplicitParams({ @ApiImplicitParam(name = "jobClassName", value = "任务class名称", dataType = "String"),
                         @ApiImplicitParam(name = "jobGroupName", value = "任务分组名称", dataType = "String"),
                         @ApiImplicitParam(name = "cronExpression", value = "任务时间表达式  ", dataType = "String") })
    @DeleteMapping(value = "/deleteSingleJob")
    public void deleteSingleJob(@RequestParam(value = "jobClassName") String jobClassName,
        @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        deleteJob(jobClassName, jobGroupName);
    }

    /**
     * 批量删除定时任务
     *
     * @param list
     * @throws Exception
     */
    @ApiOperation("批量删除定时任务")
    @ApiImplicitParam(name = "list", value = "任务数组", dataType = "JobAndTrigger")
    @DeleteMapping(value = "/deleteBatchJob")
    public void deleteBatchJob(List<JobAndTrigger> list) throws Exception {
        List<JobKey> jobKeyList = new ArrayList<JobKey>();
        for (JobAndTrigger jobAndTrigger : list) {
            jobKeyList.add(JobKey.jobKey(jobAndTrigger.getJobClassName(), jobAndTrigger.getJobGroup()));
        }
        deleteJobs(jobKeyList);
    }

    /**
     * 重新执行计划
     *
     * @param jobClassName
     * @param jobGroupName
     * @param cronExpression
     * @throws Exception
     */
    @ApiOperation("重新执行计划")
    @ApiImplicitParams({ @ApiImplicitParam(name = "jobClassName", value = "任务class名称", dataType = "String"),
                         @ApiImplicitParam(name = "jobGroupName", value = "任务分组名称", dataType = "String"),
                         @ApiImplicitParam(name = "cronExpression", value = "任务时间表达式  ", dataType = "String") })
    @PutMapping(value = "/rescheduleSingleJob")
    public void rescheduleSingleJob(@RequestParam(value = "jobClassName") String jobClassName,
        @RequestParam(value = "jobGroupName") String jobGroupName,
        @RequestParam(value = "cronExpression") String cronExpression) throws Exception {
        rescheduleJob(jobClassName, jobGroupName, cronExpression);
    }

    /**
     * 分页查询
     * 
     * @param m
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @ApiOperation("分页查询定时任务")
    @ApiImplicitParams({ @ApiImplicitParam(name = "pageNum", value = "当前页", dataType = "Integer"),
                         @ApiImplicitParam(name = "pageSize", value = "分页大小", dataType = "Integer"),
                         @ApiImplicitParam(name = "orderBy", value = "排序字段  ", dataType = "String") })
    @GetMapping(value = "/queryjob")
    public Map<String, Object> queryjob(T m, @RequestParam(value = "pageNum") Integer pageNum,
        @RequestParam(value = "pageSize") Integer pageSize, String orderBy) {
        Page<JobAndTrigger> page = new Page<>();
        page.setPageNum(pageNum);
        page.setPageSize(pageSize);
        page.setOrderBy(orderBy);
        PageInfo<JobAndTrigger> jobAndTrigger = iJobAndTriggerService.getJobAndTriggerDetails(page);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("JobAndTrigger", jobAndTrigger);
        map.put("number", jobAndTrigger.getTotal());
        return map;
    }

    /**
     * 触发一个job
     *
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @PutMapping(value = "/triggerJob")
    public void executeStoredJob(@RequestParam(value = "jobClassName") String jobClassName,
        @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        triggerJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    private void executeSimpleJob(String jobClassName, String jobGroupName) throws Exception {
        JobDetail jobDetail = newJob(getClass(jobClassName).getClass())
            .withIdentity(jobClassName, jobGroupName).build();
        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
            .withIdentity(jobClassName, jobGroupName)
            .startNow()
            .build();
        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 暂停一个job
     *
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @PutMapping(value = "/pauseSingleJob")
    public void pauseSingleJob(@RequestParam(value = "jobClassName") String jobClassName,
        @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        pauseJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    /**
     * 暂停多个job
     *
     * @param list
     * @throws Exception
     */
    @PutMapping(value = "/pauseBatchJob")
    public void pauseBatchJob(List<JobAndTrigger> list) throws Exception {
        for (JobAndTrigger jobAndTrigger : list) {
            pauseJob(JobKey.jobKey(jobAndTrigger.getJobClassName(), jobAndTrigger.getJobGroup()));
        }
    }

    /**
     *
     * 恢复一个job
     *
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    @PutMapping(value = "/resumeSingleJob")
    public void resumeSingleJob(@RequestParam(value = "jobClassName") String jobClassName,
        @RequestParam(value = "jobGroupName") String jobGroupName) throws Exception {
        resumeJob(jobClassName, jobGroupName);
    }

    /**
     *
     * 恢复多个job
     *
     * @param list
     * @throws Exception
     */
    @PutMapping(value = "/resumeBatchJob")
    public void resumeSingleJob(List<JobAndTrigger> list) throws Exception {
        for (JobAndTrigger jobAndTrigger : list) {
            resumeJob(JobKey.jobKey(jobAndTrigger.getJobClassName(), jobAndTrigger.getJobGroup()));
        }
    }

    @PutMapping(value = "/pauseAllJob")
    public void pauseAllJob() throws SchedulerException {
        pauseAll();
    }

    @PutMapping(value = "/resumeAllJob")
    public void resumeAllJob() throws SchedulerException {
        resumeAll();
    }

    private void rescheduleJob(String jobClassName, String jobGroupName, String cronExpression) throws Exception {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobClassName, jobGroupName);
            // 表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            // 按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            System.out.println("更新定时任务失败" + e);
            throw new Exception("更新定时任务失败");
        }
    }

    private void addJob(String jobClassName, String jobGroupName, String cronExpression) throws Exception {

        // 启动调度器
        scheduler.start();

        //构建job信息
        JobDetail jobDetail = newJob(getClass(jobClassName).getClass())
            .withIdentity(jobClassName, jobGroupName).build();

        //表达式调度构建器(即任务执行的时间)
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

        //按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName)
            .withSchedule(scheduleBuilder).build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);

        } catch (SchedulerException e) {
            System.out.println("创建定时任务失败" + e);
            throw new Exception("创建定时任务失败");
        }
    }

    public static BaseJob getClass(String classname) throws Exception {
        Class<?> class1 = Class.forName(classname);
        return (BaseJob) class1.newInstance();
    }

    /**
     * 删除一个job
     *
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    public void deleteJob(String jobClassName, String jobGroupName) throws Exception {
        scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
        scheduler.interrupt(JobKey.jobKey(jobClassName, jobGroupName));
        scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    /**
     * 删除相关的多个job任务
     * @param jobKeys
     * @return
     * @throws SchedulerException
     */
    private boolean deleteJobs(List<JobKey> jobKeys)
        throws SchedulerException {
        return scheduler.deleteJobs(jobKeys);
    }

    /**
     * 暂停一个job
     * @param jobClassName
     * @param jobGroupName
     * @throws Exception
     */
    private void resumeJob(String jobClassName, String jobGroupName) throws Exception {
        scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
    }

    /**
     * 启动一个job
     * @param jobkey
     * @throws SchedulerException
     */
    private void triggerJob(JobKey jobkey) throws SchedulerException {
        scheduler.triggerJob(jobkey);
    }

    /**
     *启动一个job，参数
     * @param jobkey
     * @param jobdatamap
     * @throws SchedulerException
     */
    private void triggerJob(JobKey jobkey, JobDataMap jobdatamap)
        throws SchedulerException {
        scheduler.triggerJob(jobkey, jobdatamap);
    }

    /**
     * 暂停一个job任务
     * @param jobkey
     * @throws SchedulerException
     */
    private void pauseJob(JobKey jobkey) throws SchedulerException {
        scheduler.pauseJob(jobkey);
    }

    /**
     * 暂停多个job任务
     * @param groupmatcher
     * @throws SchedulerException
     */
    private void pauseJobs(GroupMatcher<JobKey> groupmatcher)
        throws SchedulerException {
        scheduler.pauseJobs(groupmatcher);
    }

    /**
     * 暂停调度中所有的job任务
     * @throws SchedulerException
     */
    private void pauseAll() throws SchedulerException {
        scheduler.pauseAll();
    }

    /**
     * 恢复调度中所有的job的任务
     * @throws SchedulerException
     */
    private void resumeAll() throws SchedulerException {
        scheduler.resumeAll();
    }

    /**
     * 停止使用相关的触发器
     * @param triggerkey
     * @throws SchedulerException
     */
    private void pauseTrigger(TriggerKey triggerkey)
        throws SchedulerException {
        scheduler.pauseTrigger(triggerkey);
    }

    /**
     * 停止使用相关的多个触发器
     *
     * @param groupmatcher
     * @throws SchedulerException
     */
    private void pauseTriggers(GroupMatcher<TriggerKey> groupmatcher)
        throws SchedulerException {
        scheduler.pauseTriggers(groupmatcher);
    }

    /**
     * 恢复一个job
     * @param jobkey
     * @throws SchedulerException
     */
    private void resumeJob(JobKey jobkey) throws SchedulerException {
        scheduler.pauseJob(jobkey);
    }

    /**
     * 恢复多个job
     * @param matcher
     * @throws SchedulerException
     */
    private void resumeJobs(GroupMatcher<JobKey> matcher)
        throws SchedulerException {
        scheduler.resumeJobs(matcher);
    }

    /**
     * 恢复一个触发器
     * @param triggerkey
     * @throws SchedulerException
     */
    public void resumeTrigger(TriggerKey triggerkey)
        throws SchedulerException {
        scheduler.resumeTrigger(triggerkey);
    }

    /**
     * 恢复多个触发器
     * @param groupmatcher
     * @throws SchedulerException
     */
    private void resumeTriggers(GroupMatcher<TriggerKey> groupmatcher)
        throws SchedulerException {
        scheduler.resumeTriggers(groupmatcher);
    }

}
