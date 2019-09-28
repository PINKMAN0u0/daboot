package com.pinkman.dtboot.service.impl;

import com.pinkman.dtboot.dao.ScheduleJobMapper;
import com.pinkman.dtboot.entity.ScheduleJob;
import com.pinkman.dtboot.service.ScheduleJobService;
import com.pinkman.dtboot.utils.Constant;
import com.pinkman.dtboot.utils.DataGridResult;
import com.pinkman.dtboot.utils.Query;
import com.pinkman.dtboot.utils.SchedulerUtils;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by helen on 2018/3/9
 */
@Service
public class ScheduleJobServiceImpl implements ScheduleJobService {

    @Autowired
    private ScheduleJobMapper scheduleJobMapper;

    @Autowired
    private Scheduler scheduler;

    @Override
    public DataGridResult getPageList(Query query) {
        List<ScheduleJob> rows = scheduleJobMapper.queryList(query);
        int total = scheduleJobMapper.queryTotal(query);

        //创建DataGridResult对象
        DataGridResult dataGridResult = new DataGridResult(rows, total);
        return dataGridResult;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteBatch(Long[] jobIds) {

        for(Long jobId : jobIds){
            SchedulerUtils.deleteJob(scheduler, jobId);
        }

        //删除数据
        scheduleJobMapper.deleteBatch(jobIds);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(ScheduleJob scheduleJob) {
        scheduleJob.setCreateTime(new Date());
        scheduleJob.setStatus(Constant.ScheduleStatus.PAUSE.getValue());
        scheduleJobMapper.insertSelective(scheduleJob);

        //创建定时任务
        SchedulerUtils.createJob(scheduler, scheduleJob);
    }

    @Override
    public ScheduleJob queryObject(Long menuId) {
        return scheduleJobMapper.selectByPrimaryKey(menuId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(ScheduleJob scheduleJob) {

        SchedulerUtils.updateJob(scheduler, scheduleJob);

        scheduleJobMapper.updateByPrimaryKeySelective(scheduleJob);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void run(Long[] jobIds) {
        for(Long jobId : jobIds){
            SchedulerUtils.run(scheduler, jobId);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void pause(Long[] jobIds) {

        for(Long jobId : jobIds){
            SchedulerUtils.pauseJob(scheduler, jobId);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("list", jobIds);
        map.put("status", Constant.ScheduleStatus.PAUSE.getValue());
        scheduleJobMapper.updateBatch(map);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void resume(Long[] jobIds) {

        for(Long jobId : jobIds){
            SchedulerUtils.resumeJob(scheduler, jobId);
        }

        Map<String, Object> map = new HashMap<>();
        map.put("list", jobIds);
        map.put("status", Constant.ScheduleStatus.NORMAL.getValue());
        scheduleJobMapper.updateBatch(map);
    }
}
