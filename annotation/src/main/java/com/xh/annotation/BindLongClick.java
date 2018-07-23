package com.xh.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 2018/7/16 10:35
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface BindLongClick {
    String[] names() default {};
}
