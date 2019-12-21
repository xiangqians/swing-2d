package com.xiangqian.s2d.util;

import java.util.UUID;

/**
 * 字符串工具类
 *
 * @author xiangqian
 * @date 14:52 2019/11/17
 */
public class StringUtils {

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String trim(Object obj) {
        return obj == null ? "" : obj.toString().trim();
    }

}
