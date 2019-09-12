package com.pinkman.dtboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.pinkman.dtboot.dao")//扫描Dao路径
public class DtbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DtbootApplication.class, args);
    }

}
