package com.pinkman.dtboot.service.impl;

import com.pinkman.dtboot.dao.ScheduleJobLogMapper;
import com.pinkman.dtboot.entity.ScheduleJobLog;
import com.pinkman.dtboot.service.ScheduleJobLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: PINKMAN
 * @create: 2019-09-28 03:36
 **/
@Service
public class ScheduleJobLogServiceImpl implements ScheduleJobLogService {

    @Autowired
    private ScheduleJobLogMapper scheduleJobLogMapper;

    @Override
    public void save(ScheduleJobLog scheduleJobLog) {
        scheduleJobLogMapper.insertSelective(scheduleJobLog);
    }
}
