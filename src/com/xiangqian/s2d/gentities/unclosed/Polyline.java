package com.xiangqian.s2d.gentities.unclosed;

import com.xiangqian.s2d.gentities.GraphicsType;
import lombok.Data;

import java.util.List;


/**
 * 折线
 *
 * @author xiangqian
 * @date 14:58 2019/10/27
 */
@Data
public class Polyline extends UnclosedGraphics {

    private Point moveToPoint;
    private List<Point> lineToPointList; // 有序集合

    public Polyline() {
        setType(GraphicsType.POLYLINE);
    }
}
