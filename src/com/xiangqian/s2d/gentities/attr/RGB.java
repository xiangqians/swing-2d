package com.xiangqian.s2d.gentities.attr;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 颜色
 *
 * @author xiangqian
 * @date 10:10 2019/11/30
 */
@Data
@NoArgsConstructor
public class RGB implements Serializable, Cloneable {
    private static final long serialVersionUID = -5907620690302202306L;
    private int r; // red
    private int g; // green
    private int b; // blue

    public RGB(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    @Override
    public RGB clone() {
        try {
            return (RGB) super.clone();
        } catch (CloneNotSupportedException e) {
        }
        return null;
    }
}
