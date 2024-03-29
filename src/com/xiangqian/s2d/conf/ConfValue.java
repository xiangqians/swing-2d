package com.xiangqian.s2d.conf;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置文件映射Key注解
 *
 * @author xiangqian
 * @date 12:07 2019/12/01
 */
@Target(value = {ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfValue {

    String value();

}
