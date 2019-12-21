package com.xiangqian.s2d.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiangqian.s2d.gentities.Graphics;
import com.xiangqian.s2d.gentities.closed.Image;
import com.xiangqian.s2d.shared.SharedPool;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiangqian
 * @date 14:59 2019/10/27
 */
@Slf4j
public class JSONUtils {

    public static void main(String[] args) {
        try {
//            test1();
            test2();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    public static void test1() {

        Graphics graphics = new Image();
        graphics.setId(StringUtils.uuid());
        String json = toJson(graphics);
        log.debug("json=" + json);

    }

    public static void test2() {
        log.debug("1--> " + SharedPool.get(SharedPool.MainFrameShare.class));
        log.debug("2--> " + SharedPool.get(SharedPool.MainFrameShare.class));
        log.debug("3--> " + SharedPool.get(SharedPool.MainFrameShare.class));
    }

    public static String toJson(Object obj) {
        try {
            return new Gson().toJson(obj);
        } catch (Exception e) {
            log.warn("", e);
            return null;
        }
    }

    public static <T> T toEntity(String json, TypeToken<T> typeToken) {
        try {
            return new Gson().fromJson(json, typeToken.getType());
        } catch (Exception e) {
            log.warn("", e);
            return null;
        }
    }

    public static <T> T toEntity(String json, Class<T> t) {
        try {
            return new Gson().fromJson(json, t);
        } catch (Exception e) {
            log.warn("", e);
            return null;
        }
    }

}
