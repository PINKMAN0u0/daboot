package com.pinkman.dtboot.config;

import com.pinkman.dtboot.shiro.UserRealm;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @description: Shiro配置
 * @author: PINKMAN
 * @create: 2019-09-16 23:09
 **/
@Configuration
public class ShiroConfig {

    /**
     * @description: 会话管理
     * @param
     * @return: org.apache.shiro.session.mgt.SessionManager
     */
    @Bean
    public SessionManager sessionManager(){

        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //设置Session过期时间为1h,单位是毫秒（默认值是30分钟）
        sessionManager.setGlobalSessionTimeout(60 * 60 * 1000);

        //扫描Session线程，清理超时会话
        sessionManager.setSessionValidationSchedulerEnabled(true);

        //禁用URL重写（cookie被禁用后在url后追加sessionID）
        sessionManager.setSessionIdUrlRewritingEnabled(false);

        return sessionManager;
    }

    /**
     * @description: 安全管理
     * @param userRealm
     * @param sessionManager
     * @return: org.apache.shiro.mgt.SecurityManager
     */
    @Bean
    public SecurityManager securityManager(UserRealm userRealm, SessionManager sessionManager){

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setSessionManager(sessionManager);


        return securityManager;
    }


    /**
     * @description: 拦截器
     * @param
     * @return: org.apache.shiro.spring.web.ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        //为过滤器分配安全管理器
        shiroFilter.setSecurityManager(securityManager);
        //设定默认登录页
        shiroFilter.setLoginUrl("/login.html");
        //认证成功跳转到主页
        shiroFilter.setSuccessUrl("/index.html");
        //未授权时跳转链接
        shiroFilter.setUnauthorizedUrl("noauth.html");

        Map<String, String> filterMap = new LinkedHashMap<>();
        ///  "anon"什么都不做,直接放行
        // "/**"代表当前路径和路径下的所有子路径
        filterMap.put("/public/**", "anon");
        filterMap.put("/login.html", "anon");
        filterMap.put("/sys/login", "anon");
        filterMap.put("/captcha.jpg", "anon");
        //  "authc"该过滤器下的页面必须验证后才能访问，它实际是shiro内部的一个拦截器
        filterMap.put("/**", "authc");

        shiroFilter.setFilterChainDefinitionMap(filterMap);

        return shiroFilter;

    }

    /**
     * @description: Shiro生命周期处理器 ---可以自定的来调用配置在 Spring IOC 容器中 shiro bean 的生命周期方法.
     * @param
     * @return: org.apache.shiro.spring.LifecycleBeanPostProcessor
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    /**
     * @description: 开启shiro注解 ----启用 IOC 容器中使用 shiro 的注解. 但必须在配置了 LifecycleBeanPostProcessor
     * 之后才可以使用
     * @param
     * @return: org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    /**
     * @description: 开启shiro注解 ---- 注解权限
     * @param securityManager
     * @return: org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }


}
