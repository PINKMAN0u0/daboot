package com.pinkman.dtboot.config;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

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
    @Qualifier("druidDataSource")
    public DataSource druidDataSource(){
        System.out.println("------------------DruidDataSource---------------");
        DruidDataSource dataSource = new DruidDataSource();

        //设置代理过滤器
        List<Filter> filters = new ArrayList<Filter>();
        filters.add(wallFilter());
        dataSource.setProxyFilters(filters);
        return dataSource;
    }

    /**
     * @description: 设置可以执行多sql语句
     * @param
     * @return: com.alibaba.druid.wall.WallFilter
     */
    @Bean
    public WallFilter wallFilter(){

        WallFilter wallFilter = new WallFilter();
        //设置允许多sql执行
        WallConfig config = new WallConfig();
        config.setMultiStatementAllow(true);
        wallFilter.setConfig(config);

        return wallFilter;
    }
}
