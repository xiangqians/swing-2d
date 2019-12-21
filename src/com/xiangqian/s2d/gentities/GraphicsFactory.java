package com.xiangqian.s2d.gentities;

import com.xiangqian.s2d.gentities.closed.*;
import com.xiangqian.s2d.gentities.unclosed.Line;
import com.xiangqian.s2d.gentities.unclosed.Point;
import com.xiangqian.s2d.gentities.unclosed.Polyline;
import com.xiangqian.s2d.util.StringUtils;

import java.util.ArrayList;

/**
 * 图形工厂
 *
 * @author xiangqian
 * @date 09:11 2019/11/09
 */
public class GraphicsFactory {

    public static Graphics getGraphics(GraphicsType graphicsType) {
        if (graphicsType == null) {
            return null;
        }

        Graphics graphics = null;
        switch (graphicsType) {
            case NO: // 没有任何图形
                break;

            // unclosed
            case POINT: // 点
                graphics = new Point();
                break;

            case LINE: // 线
                Line line = new Line();
                line.setPoint1(new Point());
                line.setPoint2(new Point());
                graphics = line;
                break;

            case POLYLINE: // 折线
                graphics = new Polyline();
                break;


            // closed
            case RECT: // 矩形
                graphics = new Rect();
                break;

            case IMAGE: // 图片
                graphics = new Image();
                break;

            case TEXT: // 文本
                graphics = new Text();
                break;

            case ELLIPSE: // 椭圆
                graphics = new Ellipse();
                break;

            case CIRCLE: // 圆
                graphics = new Circle();
                break;

            case POLYGON: // 多边形
                Polygon polygon = new Polygon();
                polygon.setMoveToPoint(new Point());
                polygon.setLineToPointList(new ArrayList<>());
                graphics = polygon;
                break;

            case ARROW: // 箭头
                Arrow arrow = new Arrow();
                arrow.setMoveToPoint(new Point());
                arrow.setLineToPointList(new ArrayList<>(2));
                graphics = arrow;
                break;
        }

        if (graphics != null) {
            graphics.setId(StringUtils.uuid());
        }

        return graphics;
    }

}
