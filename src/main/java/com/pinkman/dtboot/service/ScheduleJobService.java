package com.pinkman.dtboot.service;

import com.pinkman.dtboot.entity.ScheduleJob;
import com.pinkman.dtboot.utils.DataGridResult;
import com.pinkman.dtboot.utils.Query;

/**
 * Created by helen on 2018/3/9
 */
public interface ScheduleJobService {

    DataGridResult getPageList(Query query);

    /**
     * 批量删除定时任务
     */
    void deleteBatch(Long[] jobIds);

    /**
     * 保存定时任务
     */
    void save(ScheduleJob scheduleJob);

    /**
     * 查询菜单
     */
    ScheduleJob queryObject(Long jobId);

    /**
     * 更新定时任务
     */
    void update(ScheduleJob scheduleJob);

    /**
     * 立即执行
     */
    void run(Long[] jobIds);

    /**
     * 暂停运行
     */
    void pause(Long[] jobIds);

    /**
     * 恢复运行
     */
    void resume(Long[] jobIds);
}
