package com.xiangqian.s2d.conf;

import com.xiangqian.s2d.gentities.attr.RGB;
import com.xiangqian.s2d.util.IOUtils;
import com.xiangqian.s2d.util.NumberUtils;
import com.xiangqian.s2d.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author xiangqian
 * @date 14:59 2019/10/27
 */
@Slf4j
public class ConfFactory {

    public static void main(String[] args) {
        log.debug("conf -> " + get(FrameConf.class));
        log.debug("conf -> " + get(AttrConf.class));
    }

    /**
     * 获取Conf
     *
     * @param clazz 缓存的Class Key
     * @param <T>   Conf实现类
     * @return
     */
    public static <T extends Conf> T get(Class<T> clazz) {
        Conf conf = null;
        if ((conf = CacheMap.INSTANCE.get(clazz)) == null) {
            synchronized (CacheMap.class) {
                if ((conf = CacheMap.INSTANCE.get(clazz)) == null) {
                    conf = newInstance(clazz);
                    init(conf);
                    CacheMap.INSTANCE.put(clazz, conf);
                }
            }
        }
        return (T) conf;
    }

    private static <T extends Conf> T newInstance(Class<T> clazz) {
        try {
            return (T) clazz.newInstance();
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    private static void init(Conf conf) {
        if (conf == null || !conf.getClass().isAnnotationPresent(ConfValue.class)) {
            return;
        }

        String prefix = conf.getClass().getAnnotation(ConfValue.class).value();

        Field[] fieldArr = conf.getClass().getDeclaredFields();
        int len = fieldArr.length;
        Field field = null;
        for (int i = 0; i < len; i++) {
            field = fieldArr[i];
            if (field.isAnnotationPresent(ConfValue.class)) {
                String value = PropertiesConf.get(prefix, field.getAnnotation(ConfValue.class).value());
                if (value == null) {
                    continue;
                }

                Class<?> fieldClass = (Class) field.getGenericType();
//                log.debug("fieldClass=" + fieldClass);
                Object fieldValue = null;

                // 基本数据类型
                if (fieldClass.isPrimitive()) {
                    switch (fieldClass.getSimpleName()) {
                        case "int":
                            fieldValue = NumberUtils.convert2int(value);
                            break;

                        case "float":
                            fieldValue = NumberUtils.convert2float(value);
                            break;

                        default:
                            break;
                    }

                }
                // 引用数据类型
                else {
                    if (fieldClass == String.class) {
                        fieldValue = StringUtils.trim(value);

                    } else if (fieldClass == RGB.class) {
                        try {
                            Constructor<RGB> constructor = RGB.class.getConstructor(int.class, int.class, int.class);
                            String[] initargArr = value.split(",");
                            fieldValue = constructor.newInstance(
                                    NumberUtils.convert2int(initargArr.length > 0 ? initargArr[0] : 0),
                                    NumberUtils.convert2int(initargArr.length > 1 ? initargArr[1] : 0),
                                    NumberUtils.convert2int(initargArr.length > 2 ? initargArr[2] : 0)
                            );
                        } catch (Exception e) {
                            log.debug("", e);
                        }
                    }
                }

                if (fieldValue == null) {
                    continue;
                }

                try {
                    field.setAccessible(true);
                    field.set(conf, fieldValue);
                } catch (Exception e) {
                    log.error("", e);
                }
            }
        }
    }

    private static class CacheMap {
        private static final HashMap<Class<? extends Conf>, Conf> INSTANCE;

        static {
            INSTANCE = new HashMap();
        }
    }

    private static class PropertiesConf {
        private static final Properties PROPERTIES;

        static {
            InputStream is = null;
            String filename = "conf.properties";
            try {
                PROPERTIES = new Properties();
                is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
                PROPERTIES.load(is);
            } catch (Exception e) {
                log.error("加载[" + filename + "]配置文件异常：", e);
                throw new Error(e);
            } finally {
                IOUtils.quietlyClosed(is);
            }
        }

        /**
         * @param prefix 前缀
         * @param suffix 后缀
         * @return
         */
        private static String get(String prefix, String suffix) {
            return PROPERTIES.getProperty(prefix + "." + suffix);
        }
    }

}
