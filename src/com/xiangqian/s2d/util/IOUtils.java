package com.xiangqian.s2d.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * IO工具类
 *
 * @author xiangqian
 * @date 14:59 2019/10/27
 */
@Slf4j
public class IOUtils {

    public static void quietlyClosed(AutoCloseable... autoCloseables) {
        if (autoCloseables == null) {
            return;
        }
        for (AutoCloseable autoCloseable : autoCloseables) {
            if (autoCloseable != null) {
                try {
                    autoCloseable.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * 深度拷贝实例
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T extends Serializable> T copyObj(T t) {
        ObjectOutputStream oos = null;
        ByteArrayOutputStream baos = null;
        ObjectInputStream ois = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(t);
            oos.flush();
            ois = new ObjectInputStream(new ByteArrayInputStream(baos.toByteArray()));
            return (T) ois.readObject();
        } catch (Exception e) {
            log.warn("", e);
        } finally {
            quietlyClosed(ois, oos, baos);
        }
        return null;
    }
}
