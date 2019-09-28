package com.pinkman.dtboot.controller;

import com.pinkman.dtboot.annotation.MyLog;
import com.pinkman.dtboot.entity.ScheduleJob;
import com.pinkman.dtboot.service.ScheduleJobService;
import com.pinkman.dtboot.utils.DataGridResult;
import com.pinkman.dtboot.utils.Query;
import com.pinkman.dtboot.utils.R;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by helen on 2018/3/9
 */
@RestController
@RequestMapping("/schedule/job")
public class ScheduleJobController {

    @Autowired
    private ScheduleJobService scheduleJobService;

    @GetMapping("/list")
    @RequiresPermissions(value={"schedule:job:list"})
    public DataGridResult getPage(@RequestParam Map<String, Object> params){
        //查询列表数据
        Query query = new Query(params);//进一步处理参数
        return scheduleJobService.getPageList(query);
    }

    /**
     * 删除定时任务
     */
    @MyLog("删除定时任务")
    @PostMapping("/del")
    @RequiresPermissions("schedule:job:delete")
    public R delete(@RequestBody Long[] jobIds){
        scheduleJobService.deleteBatch(jobIds);

        return R.ok();
    }

    /**
     * 保存定时任务
     */
    @MyLog("保存定时任务")
    @RequestMapping("/save")
    @RequiresPermissions("schedule:job:save")
    public R save(@RequestBody ScheduleJob scheduleJob){
        scheduleJobService.save(scheduleJob);
        return R.ok();
    }

    /**
     * 定时任务信息
     */
    @GetMapping("/info/{jobId}")
    @RequiresPermissions(value={"schedule:job:info"})
    public R info(@PathVariable("jobId") Long jobId){
        ScheduleJob scheduleJob = scheduleJobService.queryObject(jobId);
        return R.ok().put("scheduleJob", scheduleJob);
    }

    @MyLog("修改定时任务")
    @PostMapping("/update")
    @RequiresPermissions(value={"schedule:job:update"})
    public R update(@RequestBody ScheduleJob scheduleJob){

        scheduleJobService.update(scheduleJob);
        return R.ok();
    }

    /**
     * 恢复定时任务
     */
    @MyLog("恢复定时任务")
    @PostMapping("/resume")
    @RequiresPermissions("schedule:job:resume")
    public R resume(@RequestBody Long[] jobIds){
        scheduleJobService.resume(jobIds);
        return R.ok();
    }

    /**
     * 暂停定时任务
     */
    @MyLog("暂停定时任务")
    @PostMapping("/pause")
    @RequiresPermissions("schedule:job:pause")
    public R pause(@RequestBody Long[] jobIds){
        scheduleJobService.pause(jobIds);
        return R.ok();
    }

    /**
     * 立即执行定时任务
     */
    @MyLog("立即执行定时任务")
    @PostMapping("/run")
    @RequiresPermissions("schedule:job:run")
    public R run(@RequestBody Long[] jobIds){
        scheduleJobService.run(jobIds);
        return R.ok();
    }
}
