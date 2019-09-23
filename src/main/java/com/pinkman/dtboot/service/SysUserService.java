package com.pinkman.dtboot.service;

import com.pinkman.dtboot.entity.SysUser;

import java.util.Set;

/**
 * @description:
 * @author: PINKMAN
 * @create: 2019-09-18 20:12
 **/
public interface SysUserService {

    SysUser queryByUserName(String username);

    Set<String> getUserPermissionsById(Long userId);
}
