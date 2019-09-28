package com.pinkman.dtboot.annotation;

import java.lang.annotation.*;

/**
 * @description:
 * @author: PINKMAN
 * @create: 2019-09-25 10:01
 **/
@Target(ElementType.METHOD) //注解放置的目标位置
@Retention(RetentionPolicy.RUNTIME) //注解在那个阶段执行
@Documented
public @interface MyLog {

    String value() default "";

}
