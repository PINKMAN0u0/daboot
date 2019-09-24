package com.pinkman.dtboot.service.impl;

import com.pinkman.dtboot.dao.SysMenuMapper;
import com.pinkman.dtboot.dao.SysUserMapper;
import com.pinkman.dtboot.entity.SysMenu;
import com.pinkman.dtboot.service.SysMenuService;
import com.pinkman.dtboot.utils.DataGridResult;
import com.pinkman.dtboot.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: PINKMAN
 * @create: 2019-09-11 01:48
 **/
@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysMenu> queryListAll() {
        return sysMenuMapper.queryListAll();
    }

    @Override
    public DataGridResult getPageList(Query query) {

        List<SysMenu> rows = sysMenuMapper.queryList(query);
        int total = sysMenuMapper.queryTotal();

        DataGridResult dataGridResult = new DataGridResult(rows,total);

        return dataGridResult;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteBath(Long[] ids) {
        sysMenuMapper.deleteBath(ids);
    }

    @Override
    public List<SysMenu> queryNoButtonList() {
        return sysMenuMapper.queryNoButtonList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(SysMenu sysMenu) {
        sysMenuMapper.insertSelective(sysMenu);
    }

    @Override
    public SysMenu queryObject(Long menuId) {
        return sysMenuMapper.selectByPrimaryKey(menuId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(SysMenu sysMenu) {
        sysMenuMapper.updateByPrimaryKey(sysMenu);
    }

    /**
     * 获取用户菜单列表
     */
    @Override
    public List<SysMenu> getUserMenuList(Long userId) {
        //系统管理员，拥有最高权限
        if(userId == 1){
            return getAllMenuList(null);
        }

        //用户菜单id列表
        List<Long> userMenuIdList = sysUserMapper.queryAllMenuIdByUserId(userId);

        return getAllMenuList(userMenuIdList);
    }

    /**
     * @description: 根据菜单id列表获取当前用户可见的子菜单列表
     * @param parentId
     * @param menuIdList
     * @return: java.util.List<com.pinkman.dtboot.entity.SysMenu>
     */
    private List<SysMenu> queryListParentId(Long parentId,List<Long> menuIdList){

        //根据父id获取子菜单列表
        List<SysMenu> menuList = sysMenuMapper.queryListParentId(parentId);

        if (menuIdList == null) { //如果menuIdList==null,就不进行权限的过滤
            return menuList;
        }

        //过滤出当前用户有权访问的子菜单列表
        List<SysMenu> userMenuList = new ArrayList<>();
        for (SysMenu menu : menuList) {
            if (menuIdList.contains(menu.getMenuId())) {
                userMenuList.add(menu);
            }
        }

        return userMenuList;
    }

    /**
     * @description: 填充下一级子菜单
     * @param menuList 当前用户可访问的菜单(当前级别)
     * @param menuIdList  当前用户可访问的菜单id列表
     * @return: void
     */
    private void getMenuTreeList(List<SysMenu> menuList, List<Long> menuIdList) {

        for (SysMenu menu : menuList) {
            if (menu.getType() == 0) { //目录
                //根据菜单id获取当前用户可见的子菜单列表
                List<SysMenu> subMenuList = queryListParentId(menu.getMenuId(), menuIdList);
                //递归获取子菜单
                getMenuTreeList(subMenuList, menuIdList);

                menu.setList(subMenuList);
            }

        }
    }

    /**
     * @description: 获取用户的所有菜单列表
     * @param userMenuIdList
     * @return: java.util.List<com.pinkman.dtboot.entity.SysMenu>
     */
    private  List<SysMenu> getAllMenuList(List<Long> userMenuIdList) {

        //获取顶层节点列表
        List<SysMenu> menuList = queryListParentId(0L, userMenuIdList);

        //递归获取子菜单
        getMenuTreeList(menuList, userMenuIdList);

        return menuList;


    }

}
