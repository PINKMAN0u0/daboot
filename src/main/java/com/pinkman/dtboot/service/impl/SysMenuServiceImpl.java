package com.pinkman.dtboot.service.impl;

import com.pinkman.dtboot.dao.SysMenuMapper;
import com.pinkman.dtboot.entity.SysMenu;
import com.pinkman.dtboot.service.SysMenuService;
import com.pinkman.dtboot.utils.DataGridResult;
import com.pinkman.dtboot.utils.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void deleteBath(Long[] ids) {
        sysMenuMapper.deleteBath(ids);
    }
}
