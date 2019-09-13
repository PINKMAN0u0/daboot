package com.pinkman.dtboot.service;

import com.pinkman.dtboot.entity.SysMenu;
import com.pinkman.dtboot.utils.DataGridResult;
import com.pinkman.dtboot.utils.Query;

import java.util.List;

/**
 * @description:
 * @author: PINKMAN
 * @create: 2019-09-11 01:47
 **/
public interface SysMenuService {

    List<SysMenu> queryListAll();

    DataGridResult getPageList(Query query);

    void deleteBath(Long[] ids);

    List<SysMenu> queryNoButtonList();

    void save(SysMenu sysMenu);

    SysMenu queryObject(Long menuId);

    void update(SysMenu sysMenu);
}
