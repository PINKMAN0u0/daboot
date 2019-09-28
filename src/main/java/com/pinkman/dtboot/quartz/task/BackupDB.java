package com.pinkman.dtboot.quartz.task;

import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: PINKMAN
 * @create: 2019-09-26 21:33
 **/
@Component("backupDB")
public class BackupDB {

    public void backup(String projectname) {
        System.out.println("备份数据库");
    }

}


