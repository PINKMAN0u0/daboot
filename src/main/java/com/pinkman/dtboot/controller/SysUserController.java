package com.pinkman.dtboot.controller;

import com.pinkman.dtboot.entity.SysUser;
import com.pinkman.dtboot.utils.R;
import com.pinkman.dtboot.utils.ShiroUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 用户控制层
 * @author: PINKMAN
 * @create: 2019-09-20 23:41
 **/
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends  AbstractController{

    @GetMapping("/info")
    public R info() {
        //使用抽象类辅助构建方法
        return R.ok().put("user", getUser());
    }
}
