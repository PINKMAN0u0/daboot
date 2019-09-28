package com.pinkman.dtboot.aop;

import com.alibaba.fastjson.JSON;
import com.pinkman.dtboot.annotation.MyLog;
import com.pinkman.dtboot.entity.SysLog;
import com.pinkman.dtboot.service.SysLogService;
import com.pinkman.dtboot.utils.HttpContextUtils;
import com.pinkman.dtboot.utils.IPUtils;
import com.pinkman.dtboot.utils.ShiroUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * @description: 系统日志：切面处理类
 * @author: PINKMAN
 * @create: 2019-09-25 10:12
 **/
@Aspect
@Component
public class SysLogAspect {

    @Autowired
    private SysLogService sysLogService;

    //定义切入点
    //annotation表示注解出现的位置切入代码
    @Pointcut("@annotation(com.pinkman.dtboot.annotation.MyLog)")
    public void logPointCut(){

    }

    @AfterReturning("logPointCut()")
    public void saveSysLog(JoinPoint joinPoint){

        System.out.println("-----------切面---------------");

        //保存日志
        SysLog sysLog = new SysLog();

        //从切面织入点处通过反射机制获取织入点处的方法
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();

        //操作记录
        MyLog myLog = method.getAnnotation(MyLog.class);
        if (myLog != null) {
            String value = myLog.value();
            sysLog.setOperation(value);
        }

        //请求的方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = method.getName();
        sysLog.setMethod(className+"."+methodName);

        //请求的参数
        Object args = joinPoint.getArgs();
        String params = JSON.toJSONString(args);
        sysLog.setParams(params);

        sysLog.setCreateDate(new Date());
        sysLog.setUsername(ShiroUtils.getUserEntity().getUsername());
        HttpServletRequest  request = HttpContextUtils.getHttpServletRequest();
        sysLog.setIp(IPUtils.getIpAddr(request));

        sysLogService.save(sysLog);
    }

}
