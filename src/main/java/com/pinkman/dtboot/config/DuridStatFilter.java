package com.pinkman.dtboot.config;

import com.alibaba.druid.support.http.WebStatFilter;

import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.xml.ws.WebFault;

/**
 * @description: 配置Durid的过滤规则，监测所有的页面，并排除以下exclusions中配置的文件路径
 * @author: PINKMAN
 * @create: 2019-09-14 17:03
 **/
@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*",
        initParams = {
            //设置忽略的资源路径
            @WebInitParam(name="exclusions", value = "*.js, *.gif, *.jpg, *.bmp, *.png, *.css, *.ico, /durid/*")
        }
)
public class DuridStatFilter extends WebStatFilter {


}
