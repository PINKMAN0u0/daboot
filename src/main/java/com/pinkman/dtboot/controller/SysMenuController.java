package com.pinkman.dtboot.controller;

import com.pinkman.dtboot.entity.SysMenu;
import com.pinkman.dtboot.service.SysMenuService;
import com.pinkman.dtboot.utils.DataGridResult;
import com.pinkman.dtboot.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: PINKMAN
 * @create: 2019-09-11 02:05
 **/
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/listall")
    public List<SysMenu> getAll(){
        return sysMenuService.queryListAll();
    }

    @GetMapping("/list")
    public DataGridResult getPageList(@RequestParam Map<String,Object> params){

        Query query = new Query(params);

        DataGridResult r = sysMenuService.getPageList(query);
        return r;
    }
}
