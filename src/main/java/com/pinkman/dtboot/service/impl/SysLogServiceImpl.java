package com.pinkman.dtboot.service.impl;

import com.pinkman.dtboot.dao.SysLogMapper;
import com.pinkman.dtboot.entity.SysLog;
import com.pinkman.dtboot.service.SysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: PINKMAN
 * @create: 2019-09-25 10:44
 **/
@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    @Override
    public void save(SysLog sysLog) {

        sysLogMapper.insertSelective(sysLog);

    }
}
