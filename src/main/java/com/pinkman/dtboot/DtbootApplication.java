package com.pinkman.dtboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement//开启事务
@MapperScan("com.pinkman.dtboot.dao")//扫描Dao路径
public class DtbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(DtbootApplication.class, args);
    }

}
