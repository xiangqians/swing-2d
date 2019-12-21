package com.xiangqian.s2d.util;

/**
 * @author xiangqian
 * @date 20:41 2019/11/30
 */
public class NumberUtils {

    public static int convert2int(Object obj) {
        try {
            return Integer.parseInt(StringUtils.trim(obj));
        } catch (Exception e) {
        }
        return -1;
    }

    public static double convert2double(Object obj) {
        try {
            return Double.parseDouble(StringUtils.trim(obj));
        } catch (Exception e) {
        }
        return -1;
    }

    public static float convert2float(Object obj) {
        try {
            return Float.parseFloat(StringUtils.trim(obj));
        } catch (Exception e) {
        }
        return -1;
    }
}
