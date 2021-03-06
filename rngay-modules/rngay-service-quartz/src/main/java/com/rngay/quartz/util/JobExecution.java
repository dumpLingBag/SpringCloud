package com.rngay.quartz.util;

import com.rngay.feign.quartz.dto.JobDTO;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobExecution extends AbstractQuartzJob {

    private static final Logger logger = LoggerFactory.getLogger(JobExecution.class);

    /**
     * 核心方法,Quartz Job真正的执行逻辑.
     * @param context: 封装有Quartz运行所需要的所有信息
     * @return: void
     * @date 2021/5/1 下午9:27
     * @auther dongpengcheng
     */
    @Override
    public void doExecute(JobExecutionContext context, JobDTO jobDTO) {
        logger.info("JobExecution doExecute --->");
        try {
            invokeMethod(jobDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
