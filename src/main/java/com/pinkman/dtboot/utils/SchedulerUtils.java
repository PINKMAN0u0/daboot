package com.pinkman.dtboot.utils;

import com.alibaba.fastjson.JSON;
import com.pinkman.dtboot.entity.ScheduleJob;
import com.pinkman.dtboot.quartz.QuartzJob;
import com.pinkman.dtboot.utils.RRException;
import org.quartz.*;

/**
 * @description:
 * @author: PINKMAN
 * @create: 2019-09-26 19:59
 **/
public class SchedulerUtils {

    public static void createJob(Scheduler scheduler,  ScheduleJob scheduleJob){

        try{
            Long jobId = scheduleJob.getJobId();

            //创建Job对象
            JobDetail job = JobBuilder.newJob(QuartzJob.class).withIdentity("JOB_" + jobId).build();

            //创建触发器
            //withMisfireHandlingInstructionDoNothing()  错过的执行任务就跳过
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
                    .withMisfireHandlingInstructionDoNothing();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("TRIGGER_" + jobId)
                    .withSchedule(cronScheduleBuilder)
                    .build();

            //存数据
            job.getJobDataMap().put("JOB_PARAM_KEY", JSON.toJSONString(scheduleJob));

            scheduler.scheduleJob(job, trigger);
            //创建出来立即暂停
            scheduler.pauseJob(JobKey.jobKey("JOB_" + jobId));

        }catch (SchedulerException e){
            throw new RRException("创建任务失败", e);
        }

    }

    public static void resumeJob(Scheduler scheduler, Long jobId){
        try {
            scheduler.resumeJob(JobKey.jobKey("JOB_" + jobId));
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RRException("恢复定时任务失败", e);
        }
    }

    public static void pauseJob(Scheduler scheduler, Long jobId){
        try {
            scheduler.pauseJob(JobKey.jobKey("JOB_" + jobId));
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RRException("暂停定时任务失败", e);
        }
    }

    public static void run(Scheduler scheduler, Long jobId){
        try {
            scheduler.triggerJob(JobKey.jobKey("JOB_" + jobId));
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RRException("立即执行定时任务失败", e);
        }
    }

    public static void updateJob(Scheduler scheduler,  ScheduleJob scheduleJob){

        //获取新的cron表达式
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression())
                .withMisfireHandlingInstructionDoNothing();

        Long jobId = scheduleJob.getJobId();
        try {

            //拿到原有的trigger
            TriggerKey triggerKey = TriggerKey.triggerKey("TRIGGER_" + jobId);
            CronTrigger trigger = (CronTrigger)scheduler.getTrigger(triggerKey);
            //为原有的trigger赋予新的cron表达式
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();

            //执行原有的trigger的更新
            scheduler.rescheduleJob(triggerKey, trigger);

        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RRException("更新定时任务失败", e);
        }

    }

    public static void deleteJob(Scheduler scheduler, Long jobId){
        try {
            scheduler.deleteJob(JobKey.jobKey("JOB_" + jobId));
        } catch (SchedulerException e) {
            e.printStackTrace();
            throw new RRException("删除定时任务失败", e);
        }
    }
}
