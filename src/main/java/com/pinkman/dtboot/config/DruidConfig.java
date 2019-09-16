package com.pinkman.dtboot.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @description: Druid配置类
 * @author: PINKMAN
 * @create: 2019-09-14 16:05
 **/
@Configuration
public class DruidConfig {

    @Bean
    //配置数据源
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(){
        System.out.println("------------------DruidDataSource---------------");
        return new DruidDataSource();
    }
}
