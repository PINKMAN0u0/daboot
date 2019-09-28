package com.pinkman.dtboot.quartz;

import com.alibaba.fastjson.JSON;
import com.pinkman.dtboot.entity.ScheduleJob;
import com.pinkman.dtboot.entity.ScheduleJobLog;
import com.pinkman.dtboot.service.ScheduleJobLogService;
import com.pinkman.dtboot.utils.SpringContextUtils;
import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @description:
 * @author: PINKMAN
 * @create: 2019-09-26 19:48
 **/
public class QuartzJob extends QuartzJobBean {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        //System.out.println("执行任务");

        //记录开始时间
        Long startTime = System.currentTimeMillis();

        String jsonJob = context.getMergedJobDataMap().getString("JOB_PARAM_KEY");
        ScheduleJob scheduleJob = JSON.parseObject(jsonJob, ScheduleJob.class);

        Long jobId = scheduleJob.getJobId();

        String beanName = scheduleJob.getBeanName();
        String methodName = scheduleJob.getMethodName();
        String params = scheduleJob.getParams();

        //获取spring bean
        ScheduleJobLogService scheduleJobLogService = (ScheduleJobLogService)SpringContextUtils.getBean("scheduleJobLogServiceImpl");
        //设置jobLog
        ScheduleJobLog log = new ScheduleJobLog();
        log.setJobId(jobId);
        log.setBeanName(beanName);
        log.setMethodName(methodName);
        log.setParams(params);
        log.setCreateTime(new Date());

        try {

            Object target = SpringContextUtils.getBean(beanName);
            Method method = null;
            if (StringUtils.isNotBlank(params)) {
                method = target.getClass().getDeclaredMethod(methodName, String.class);
            }else {
                method = target.getClass().getDeclaredMethod(methodName);
            }

            ReflectionUtils.makeAccessible(method);
            if (StringUtils.isNotBlank(params)) {
                method.invoke(target,params);
            }else {
                method.invoke(target);
            }


            //正确的话为0
            log.setStatus((byte)0);
            //计算耗费总时间
            Long times = System.currentTimeMillis() - startTime;
            //记录耗费总时间
            log.setTimes(times);


            logger.info("任务执行成功，任务ID："+jobId+",总耗时：" + times + "毫秒");

        } catch (Exception e) {
            //计算耗费总时间
            Long times = System.currentTimeMillis() - startTime;

            log.setError(StringUtils.substring(e.toString(),2000));
            //错误的话为1
            log.setStatus((byte)1);
            //记录耗费总时间
            log.setTimes(times);

            e.printStackTrace();
            logger.error("任务执行失败,任务ID："+jobId);
        } finally {
            scheduleJobLogService.save(log);
        }
    }
}
