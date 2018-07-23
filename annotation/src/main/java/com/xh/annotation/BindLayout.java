package com.xh.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 2018/7/17 18:51
 * instructions：
 * author:liuhuiliang  email:825378291@qq.com
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface BindLayout {
    String layout() default "";
}
