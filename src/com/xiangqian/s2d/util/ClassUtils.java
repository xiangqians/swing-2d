package com.xiangqian.s2d.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author xiangqian
 * @date 13:22 2019/12/01
 */
public class ClassUtils {

    public static <T> Class<T> getGenericSuperclass(Class<?> clazz) {
        Type type = clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type[] types = parameterizedType.getActualTypeArguments();
            if (types != null && types.length > 0) {
                if (types[0] instanceof Class) {
                    return (Class<T>) types[0];
                }
            }
        }
        return null;
    }


}
