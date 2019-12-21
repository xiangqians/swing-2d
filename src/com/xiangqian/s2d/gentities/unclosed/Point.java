package com.xiangqian.s2d.gentities.unclosed;

import com.xiangqian.s2d.gentities.Graphics;
import com.xiangqian.s2d.gentities.GraphicsType;
import lombok.Data;

/**
 * @author xiangqian
 * @date 14:57 2019/10/27
 */
@Data
public class Point extends Graphics {

    private double x;
    private double y;

    public Point() {
        setType(GraphicsType.POINT);
    }

    public Point(double x, double y) {
        this();
        this.x = x;
        this.y = y;
    }
}
