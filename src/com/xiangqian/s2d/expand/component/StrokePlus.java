package com.xiangqian.s2d.expand.component;

import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.lang.reflect.Field;

/**
 * @author xiangqian
 * @date 12:49 2019/11/30
 */
@Slf4j
public class StrokePlus extends BasicStroke {

    private static final Field WIDTH_FIELD;

    static {
        try {
            WIDTH_FIELD = BasicStroke.class.getDeclaredField("width");
            WIDTH_FIELD.setAccessible(true);
        } catch (Exception e) {
            log.error("", e);
            throw new Error(e);
        }
    }

    public StrokePlus() {
        super();
    }

    public StrokePlus(float width) {
        super(width);
    }

    public float getWidth() {
        Float width = null;
        try {
            width = Float.valueOf(String.valueOf(WIDTH_FIELD.get(this)));
        } catch (Exception e) {
            log.error("", e);
        }
        return width == null ? 0 : width.floatValue();
    }

    public void setWidth(float width) {
        try {
            WIDTH_FIELD.set(this, width);
        } catch (Exception e) {
            log.error("", e);
        }
    }

}
