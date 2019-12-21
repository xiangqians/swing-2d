package com.xiangqian.s2d.expand.component;

import com.xiangqian.s2d.conf.AttrConf;
import com.xiangqian.s2d.conf.ConfFactory;
import com.xiangqian.s2d.gentities.attr.RGB;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author xiangqian
 * @date 13:51 2019/12/15
 */
public class ColorPlus extends Color {

    public static void main(String[] args) {
        AttrConf attrConf = ConfFactory.get(AttrConf.class);
        ColorPlus color = new ColorPlus(255, 0, 0);
        System.out.println("---" + color);
        try {
            color.setRGB(attrConf.getDefaultRgb());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("---" + color);
    }

    public ColorPlus(RGB rgb) {
        this(rgb.getR(), rgb.getG(), rgb.getB());
    }

    public ColorPlus(int r, int g, int b) {
        super(r, g, b);
    }

    public void setRGB(RGB rgb) throws Exception {

        // set value
        int r = rgb.getR();
        int g = rgb.getG();
        int b = rgb.getB();
        int a = 255;
        int value = ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | ((b & 0xFF) << 0);
        VALUE_FIELD.set(this, value);
        TEST_COLOR_VALUE_RANGE_METHOD.invoke(this, r, g, b, a);
    }


    private static final Field VALUE_FIELD;
    private static final Method TEST_COLOR_VALUE_RANGE_METHOD;

    static {
        try {
            Class<Color> clazz = Color.class;
            VALUE_FIELD = clazz.getDeclaredField("value");
            VALUE_FIELD.setAccessible(true);

            // int r, int g, int b, int a
            TEST_COLOR_VALUE_RANGE_METHOD = clazz.getDeclaredMethod("testColorValueRange", int.class, int.class, int.class, int.class);
            TEST_COLOR_VALUE_RANGE_METHOD.setAccessible(true);

        } catch (Exception e) {
            throw new Error(e);
        }
    }

}
