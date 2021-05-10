package com.rngay.feign.quartz.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.rngay.feign.dto.CommonDTO;

@TableName(value = "sys_job")
public class JobDTO extends CommonDTO {

    @TableId(type = IdType.ID_WORKER)
    private Long id;

    /*
    * 任务名称
    * */
    private String jobName;

    /*
    * 任务组名称
    * */
    private String jobGroupName;

    /*
    * 触发器名称
    * */
    private String triggerName;

    /*
    * 触发器组名称
    * */
    private String triggerGroupName;

    /*
    * 是否并发执行（0允许 1禁止）
    * */
    private String current;

    /*
    * cron执行表达式
    * */
    private String cron;

    /*
    * 任务状态（0正常 1暂停）
    * */
    private String status;

    /*
    * cron计划策略
    * 0=默认,1=立即触发执行,2=触发一次执行,3=不触发立即执行
    * */
    private String misfirePolicy;

    /*
    * 调用目标字符串
    * */
    private String invokeTarget;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroupName() {
        return jobGroupName;
    }

    public void setJobGroupName(String jobGroupName) {
        this.jobGroupName = jobGroupName;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getTriggerGroupName() {
        return triggerGroupName;
    }

    public void setTriggerGroupName(String triggerGroupName) {
        this.triggerGroupName = triggerGroupName;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMisfirePolicy() {
        return misfirePolicy;
    }

    public void setMisfirePolicy(String misfirePolicy) {
        this.misfirePolicy = misfirePolicy;
    }

    public String getInvokeTarget() {
        return invokeTarget;
    }

    public void setInvokeTarget(String invokeTarget) {
        this.invokeTarget = invokeTarget;
    }

    @Override
    public String toString() {
        return "JobDTO{" +
                "id=" + id +
                ", jobName='" + jobName + '\'' +
                ", jobGroupName='" + jobGroupName + '\'' +
                ", triggerName='" + triggerName + '\'' +
                ", triggerGroupName='" + triggerGroupName + '\'' +
                ", current='" + current + '\'' +
                ", cron='" + cron + '\'' +
                ", status='" + status + '\'' +
                ", misfirePolicy='" + misfirePolicy + '\'' +
                ", invokeTarget='" + invokeTarget + '\'' +
                '}';
    }
}
