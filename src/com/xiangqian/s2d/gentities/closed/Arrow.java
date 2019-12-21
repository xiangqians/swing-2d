package com.xiangqian.s2d.gentities.closed;

import com.xiangqian.s2d.gentities.GraphicsType;
import com.xiangqian.s2d.gentities.unclosed.Point;
import lombok.Data;

import java.util.List;

/**
 * 箭头
 *
 * @author xiangqian
 * @date 15:20 2019/10/27
 */
@Data
public class Arrow extends ClosedGraphics {

    private Point moveToPoint;
    private List<Point> lineToPointList; // 有序集合

    private double angle; // 箭头夹角
    private double height; // 箭头高度

    public Arrow() {
        setType(GraphicsType.ARROW);
    }

}
