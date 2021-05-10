package com.rngay.quartz.util;

import com.rngay.feign.quartz.dto.JobDTO;
import com.rngay.quartz.constant.ScheduleConstants;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public abstract class AbstractQuartzJob extends JobInvoke implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            Object job = jobExecutionContext.getMergedJobDataMap().get(ScheduleConstants.JOB_DATA);
            if (job instanceof JobDTO) {
                this.doExecute(jobExecutionContext, (JobDTO) job);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void doExecute(JobExecutionContext context, JobDTO jobDTO);

}
