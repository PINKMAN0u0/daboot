package com.pinkman.dtboot.controller;

import com.pinkman.dtboot.entity.SysMenu;
import com.pinkman.dtboot.service.SysMenuService;
import com.pinkman.dtboot.utils.DataGridResult;
import com.pinkman.dtboot.utils.Query;
import com.pinkman.dtboot.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    /**
     * @description: 根据menuId批量删除
     * @param menuIds
     * @return: com.pinkman.dtboot.utils.R
     */
    @PostMapping("/del")
    public R DeleteBatch(@RequestBody Long[] menuIds){

        for (Long menuId : menuIds){
            System.out.println("=-=-=-=-="+menuId+"=-=-=-=-=");
            if (menuId <= 31){
                return R.error("系统菜单，不能删除");
            }
        }

        sysMenuService.deleteBath(menuIds);
        return R.ok("删除成功");
    }

    /**
     * @description: 返回没有按钮节点的目录列表
     * @param
     * @return: com.pinkman.dtboot.utils.R
     */
    @GetMapping("/select")
    public R select(){

        List<SysMenu> sysMenus = sysMenuService.queryNoButtonList();

        SysMenu root = new SysMenu();
        root.setMenuId(0L);
        root.setName("一级菜单");
        root.setParentId(-1L);
        root.setOpen(true);

        sysMenus.add(root);


        return R.ok().put("menuList",sysMenus);
    }

    /**
     * @description: 将传入的menu数据存入数据库
     * @param menu
     * @return: com.pinkman.dtboot.utils.R
     */
    @PostMapping("/save")
    public R save(@RequestBody SysMenu menu){
        sysMenuService.save(menu);
        return R.ok("保存成功");
    }

    /**
     * @description: 返回选中目录信息
     * @param menuId
     * @return: com.pinkman.dtboot.utils.R
     */
    @GetMapping("/info/{menuId}")
    public R info(@PathVariable("menuId") Long menuId){
        SysMenu sysMenu = sysMenuService.queryObject(menuId);
        return R.ok().put("menu",sysMenu);
    }

    /**
     * @description: 修改数据
     * @param menu
     * @return: com.pinkman.dtboot.utils.R
     */
    @PostMapping("/update")
    public R update(@RequestBody SysMenu menu){
        sysMenuService.update(menu);
        return R.ok("修改成功");
    }


}
