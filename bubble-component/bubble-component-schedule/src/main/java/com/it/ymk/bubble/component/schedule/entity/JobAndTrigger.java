package com.it.ymk.bubble.component.schedule.entity;

import java.math.BigInteger;

/**
 * The type Job and trigger.
 *
 * @author yanmingkun
 * @date 2018 -01-19 09:41
 */
public class JobAndTrigger {
    private String     jobName;
    private String     jobGroup;
    private String     jobClassName;
    private String     triggerName;
    private String     triggerGroup;
    private BigInteger repeatInterval;
    private BigInteger timesTriggered;
    private String     cronExpression;
    private String     timeZoneId;
    private String     triggerStatus;
    private String     triggerType;
    private BigInteger prevFireTime;
    private BigInteger nextFireTime;

    /**
     * Gets job name.
     *
     * @return the job name
     */
    public String getJobName() {
        return jobName;
    }

    /**
     * Sets job name.
     *
     * @param jobName the job name
     */
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    /**
     * Gets job group.
     *
     * @return the job group
     */
    public String getJobGroup() {
        return jobGroup;
    }

    /**
     * Sets job group.
     *
     * @param jobGroup the job group
     */
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    /**
     * Gets job class name.
     *
     * @return the job class name
     */
    public String getJobClassName() {
        return jobClassName;
    }

    /**
     * Sets job class name.
     *
     * @param jobClassName the job class name
     */
    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    /**
     * Gets trigger name.
     *
     * @return the trigger name
     */
    public String getTriggerName() {
        return triggerName;
    }

    /**
     * Sets trigger name.
     *
     * @param triggerName the trigger name
     */
    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    /**
     * Gets trigger group.
     *
     * @return the trigger group
     */
    public String getTriggerGroup() {
        return triggerGroup;
    }

    /**
     * Sets trigger group.
     *
     * @param triggerGroup the trigger group
     */
    public void setTriggerGroup(String triggerGroup) {
        this.triggerGroup = triggerGroup;
    }

    /**
     * Gets repeat interval.
     *
     * @return the repeat interval
     */
    public BigInteger getRepeatInterval() {
        return repeatInterval;
    }

    /**
     * Sets repeat interval.
     *
     * @param repeatInterval the repeat interval
     */
    public void setRepeatInterval(BigInteger repeatInterval) {
        this.repeatInterval = repeatInterval;
    }

    /**
     * Gets times triggered.
     *
     * @return the times triggered
     */
    public BigInteger getTimesTriggered() {
        return timesTriggered;
    }

    /**
     * Sets times triggered.
     *
     * @param timesTriggered the times triggered
     */
    public void setTimesTriggered(BigInteger timesTriggered) {
        this.timesTriggered = timesTriggered;
    }

    /**
     * Gets cron expression.
     *
     * @return the cron expression
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * Sets cron expression.
     *
     * @param cronExpression the cron expression
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    /**
     * Gets time zone id.
     *
     * @return the time zone id
     */
    public String getTimeZoneId() {
        return timeZoneId;
    }

    /**
     * Sets time zone id.
     *
     * @param timeZoneId the time zone id
     */
    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    /**
     * Gets trigger status.
     *
     * @return the trigger status
     */
    public String getTriggerStatus() {
        return triggerStatus;
    }

    /**
     * Sets trigger status.
     *
     * @param triggerStatus the trigger status
     */
    public void setTriggerStatus(String triggerStatus) {
        this.triggerStatus = triggerStatus;
    }

    /**
     * Gets trigger type.
     *
     * @return the trigger type
     */
    public String getTriggerType() {
        return triggerType;
    }

    /**
     * Sets trigger type.
     *
     * @param triggerType the trigger type
     */
    public void setTriggerType(String triggerType) {
        this.triggerType = triggerType;
    }

    /**
     * Gets prev fire time.
     *
     * @return the prev fire time
     */
    public BigInteger getPrevFireTime() {
        return prevFireTime;
    }

    /**
     * Sets prev fire time.
     *
     * @param prevFireTime the prev fire time
     */
    public void setPrevFireTime(BigInteger prevFireTime) {
        this.prevFireTime = prevFireTime;
    }

    /**
     * Gets next fire time.
     *
     * @return the next fire time
     */
    public BigInteger getNextFireTime() {
        return nextFireTime;
    }

    /**
     * Sets next fire time.
     *
     * @param nextFireTime the next fire time
     */
    public void setNextFireTime(BigInteger nextFireTime) {
        this.nextFireTime = nextFireTime;
    }

}
