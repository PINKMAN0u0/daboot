package com.pinkman.dtboot.service.impl;

import com.pinkman.dtboot.dao.SysUserMapper;
import com.pinkman.dtboot.entity.SysUser;
import com.pinkman.dtboot.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @description:
 * @author: PINKMAN
 * @create: 2019-09-18 20:14
 **/
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUser queryByUserName(String username) {

        return sysUserMapper.queryByUserName(username);
    }
}
