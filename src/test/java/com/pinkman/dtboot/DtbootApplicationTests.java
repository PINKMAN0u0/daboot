package com.pinkman.dtboot;

import com.pinkman.dtboot.dao.SysMenuMapper;
import com.pinkman.dtboot.entity.SysMenu;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DtbootApplicationTests {

   @Autowired
   private SysMenuMapper sysMenuMapper;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testSelectByPrimaryKey(){

        SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(1L);
        System.out.println(sysMenu.getName());

    }

}
