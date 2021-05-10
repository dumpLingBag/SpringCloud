package com.rngay.quartz.util;

import com.rngay.common.exception.job.TaskException;
import com.rngay.feign.quartz.dto.JobDTO;
import com.rngay.quartz.constant.ScheduleConstants;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.List;

public class QuartzManager {

    private final static SchedulerFactory schedulerFactory =  new StdSchedulerFactory();

    private Scheduler scheduler = null;

    /**
     * 得到quartz任务类
     * @author pengcheng
     * @date 2021/5/3 下午10:11
     */
    private static Class<? extends Job> getQuartzJobClass(JobDTO jobDTO) {
        return "0".equals(jobDTO.getCurrent()) ? JobExecution.class : JobDisallowConcurrentExecution.class;
    }

    /**
     * 构建任务触发对象
     * @author pengcheng
     * @date 2021/5/3 下午9:08
     */
    private static TriggerKey getTriggerKey(Long jobId, String jobGroup) {
        return TriggerKey.triggerKey(ScheduleConstants.TRIGGER_KEY + jobId, jobGroup);
    }

    /**
     * 构建任务键对象
     * @author pengcheng
     * @date 2021/5/3 下午9:08
     */
    private static JobKey getJobKey(Long jobId, String jobGroup) {
        return JobKey.jobKey(ScheduleConstants.JOB_KEY + jobId, jobGroup);
    }

    /**
     * 添加一个定时任务
     * @param jobDTO:
     * @return: void
     * @date 2021/5/1 下午9:21
     * @auther dongpengcheng
     */
    public static void addJob(JobDTO jobDTO) {
        try {
            // 任务名，任务组，任务执行类
            Scheduler scheduler = schedulerFactory.getScheduler();
            Class<? extends Job> quartzJobClass = getQuartzJobClass(jobDTO);
            JobDetail jobDetail= JobBuilder.newJob(quartzJobClass).withIdentity(getJobKey(jobDTO.getId(), jobDTO.getJobGroupName())).build();

            // 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            // 触发器名,触发器组
            triggerBuilder.withIdentity(getTriggerKey(jobDTO.getId(), jobDTO.getTriggerGroupName()));

            // 触发器时间设定
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(jobDTO.getCron());
            cronScheduleBuilder = handleCronScheduleMisfirePolicy(jobDTO, cronScheduleBuilder);

            // 创建Trigger对象
            CronTrigger trigger = triggerBuilder.withSchedule(cronScheduleBuilder).build();

            // 放入参数，运行时可以获取
            jobDetail.getJobDataMap().put(ScheduleConstants.JOB_DATA, jobDTO);

            // 是否存在
            if (scheduler.checkExists(getJobKey(jobDTO.getId(), jobDTO.getJobGroupName()))) {
                // 防止创建时存在数据问题 先移除，然后在执行创建操作
                removeJob(jobDTO);
            }
            // 调度容器设置JobDetail和Trigger
            scheduler.scheduleJob(jobDetail, trigger);

            // 暂停任务
            if (jobDTO.getStatus().equals(ScheduleConstants.Status.PAUSE.getValue())) {
                scheduler.pauseJob(getJobKey(jobDTO.getId(), jobDTO.getJobGroupName()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改一个任务的触发时间
     * @param jobDTO:
     * @return: void
     * @date 2021/5/1 下午9:20
     * @auther dongpengcheng
     */
    public static void modifyJobTime(JobDTO jobDTO) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(jobDTO.getTriggerName(), jobDTO.getTriggerGroupName());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return;
            }

            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(jobDTO.getCron())) {
                /** 方式一 ：调用 rescheduleJob 开始 */
               /* // 触发器
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                // 触发器名,触发器组
                triggerBuilder.withIdentity(triggerName, triggerGroupName);
                triggerBuilder.startNow();
                // 触发器时间设定
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
                // 创建Trigger对象
                trigger = (CronTrigger) triggerBuilder.build();
                // 方式一 ：修改一个任务的触发时间
                scheduler.rescheduleJob(triggerKey, trigger);*/
                /** 方式一 ：调用 rescheduleJob 结束 */

                /** 方式二：先删除，然后在创建一个新的Job  */
                /*JobDetail jobDetail = scheduler.getJobDetail(getJobKey(jobDTO.getId(), jobDTO.getJobGroupName()));
                Class<? extends Job> jobClass = jobDetail.getJobClass();
                jobDTO.setJobClass(jobClass);*/
                removeJob(jobDTO);
                addJob(jobDTO);
                /** 方式二 ：先删除，然后在创建一个新的Job */
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除一个定时任务
     * @param jobDTO:
     * @return: void
     * @date 2021/5/1 下午9:20
     * @auther dongpengcheng
     */
    public static void removeJob(JobDTO jobDTO) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            TriggerKey triggerKey = getTriggerKey(jobDTO.getId(), jobDTO.getTriggerGroupName());

            scheduler.pauseTrigger(triggerKey);// 停止触发器
            scheduler.unscheduleJob(triggerKey);// 移除触发器
            scheduler.deleteJob(getJobKey(jobDTO.getId(), jobDTO.getJobGroupName()));// 删除任务
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 启动所有定时任务
     * @author pengcheng
     * @date 2021/5/1 下午9:23
     */
    public static void startJobs() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 启动指定定时任务
     * @param job:
     * @return: void
     * @date 2021/5/3 下午9:29
     * @auther dongpengcheng
     */
    public static void startJob(JobDTO job) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.triggerJob(getJobKey(job.getId(), job.getJobGroupName()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭所有任务
     * @author pengcheng
     * @date 2021/5/1 下午9:24
     */
    public static void shutdownJobs() {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取当前正在执行的任务
     * @author pengcheng
     * @date 2021/5/1 下午9:24
     */
    public static boolean getCurrentJobs(String name) {
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            List<JobExecutionContext> jobContexts = scheduler.getCurrentlyExecutingJobs();
            for (JobExecutionContext context : jobContexts) {
                if (name.equals(context.getTrigger().getJobKey().getName())) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static CronScheduleBuilder handleCronScheduleMisfirePolicy(JobDTO jobDTO, CronScheduleBuilder builder) {
        switch (jobDTO.getMisfirePolicy())
        {
            case ScheduleConstants.MISFIRE_DEFAULT:
                return builder;
            case ScheduleConstants.MISFIRE_IGNORE_MISFIRES:
                return builder.withMisfireHandlingInstructionIgnoreMisfires();
            case ScheduleConstants.MISFIRE_FIRE_AND_PROCEED:
                return builder.withMisfireHandlingInstructionFireAndProceed();
            case ScheduleConstants.MISFIRE_DO_NOTHING:
                return builder.withMisfireHandlingInstructionDoNothing();
            default:
                throw new TaskException("The task misfire policy '" + jobDTO.getMisfirePolicy()
                        + "' cannot be used in cron schedule tasks", TaskException.Code.CONFIG_ERROR);
        }
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
