package com.xiangqian.s2d.gentities.closed;

import com.xiangqian.s2d.gentities.GraphicsType;
import com.xiangqian.s2d.gentities.unclosed.Point;
import lombok.Data;

import java.util.List;


/**
 * 多边形
 *
 * @author xiangqian
 * @date 14:58 2019/10/27
 */
@Data
public class Polygon extends ClosedGraphics {

    private Point moveToPoint;
    private List<Point> lineToPointList; // 有序集合

    public Polygon() {
        setType(GraphicsType.POLYGON);
    }
}
