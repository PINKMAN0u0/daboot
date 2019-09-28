package com.pinkman.dtboot.quartz.task;

import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: PINKMAN
 * @create: 2019-09-28 03:23
 **/
@Component("myTask2")
public class MyTask2 {

    public void fetch() {
        System.out.println("抓取新闻");
    }


}
