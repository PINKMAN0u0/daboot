package com.pinkman.dtboot.service.impl;

import com.pinkman.dtboot.dao.SysMenuMapper;
import com.pinkman.dtboot.dao.SysUserMapper;
import com.pinkman.dtboot.entity.SysMenu;
import com.pinkman.dtboot.entity.SysUser;
import com.pinkman.dtboot.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description:
 * @author: PINKMAN
 * @create: 2019-09-18 20:14
 **/
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public SysUser queryByUserName(String username) {

        return sysUserMapper.queryByUserName(username);
    }

    @Override
    public Set<String> getUserPermissionsById(Long userId) {

        List<String> permsList = null;

        //超级管理员授权
        if (userId == 1) {
            List<SysMenu> menuList = sysMenuMapper.queryListAll();
            permsList = new ArrayList<String>(menuList.size());
            for (SysMenu menu : menuList) {
                permsList.add(menu.getPerms());
            }
        } else {
            /*通用用户授权*/
            permsList = sysUserMapper.queryAllPermsById(userId);
        }


        //用Set去重
        Set<String> permsSet = new HashSet<String>();
        //加工源数据
        for (String perms : permsList) {

            if (StringUtils.isBlank(perms)) {
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }

        return permsSet;
    }
}
