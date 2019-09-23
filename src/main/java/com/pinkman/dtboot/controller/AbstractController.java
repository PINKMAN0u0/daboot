package com.pinkman.dtboot.controller;

import com.pinkman.dtboot.entity.SysUser;
import com.pinkman.dtboot.utils.ShiroUtils;
import org.springframework.stereotype.Controller;

/**
 * @description: 抽象类
 * @author: PINKMAN
 * @create: 2019-09-21 00:24
 **/
@Controller
public abstract class AbstractController {

    protected SysUser getUser(){
        //因为此处默认从认证中返回SysUser对象，所以在UserRealm中最好将user存入
        return ShiroUtils.getUserEntity();
    }

    protected Long getUserId(){
        return getUser().getUserId();
    }
}
