package com.xiangqian.s2d.util;

import com.xiangqian.s2d.gentities.closed.Arrow;
import com.xiangqian.s2d.gentities.unclosed.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * 箭头工具类
 *
 * @author xiangqian
 * @date 13:26 2019/11/30
 */
public class ArrowUtils {

    public static Arrow buildArrow(Point linePoint1, Point linePoint2, Arrow arrow) {
        return buildArrow(linePoint1.getX(), linePoint1.getY(), linePoint2.getX(), linePoint2.getY(), arrow);
    }

    /**
     * 获取箭头实体类
     *
     * @param arrowAngle  箭头夹角
     * @param arrowHeight 箭头高度
     * @param linePoint1  线的第一个点
     * @param linePoint2  线的第二个点
     * @return
     */
    public static Arrow getArrow(double arrowAngle, double arrowHeight, Point linePoint1, Point linePoint2) {
        return getArrow(arrowAngle, arrowHeight, linePoint1.getX(), linePoint1.getY(), linePoint2.getX(), linePoint2.getY());
    }

    /**
     * 获取箭头实体类
     *
     * @param arrowAngle  箭头夹角
     * @param arrowHeight 箭头高度
     * @param x1          线的第一个点x
     * @param y1          线的第一个点y
     * @param x2          线的第二个点x
     * @param y2          线的第二个点y
     * @return
     */
    public static Arrow getArrow(double arrowAngle, double arrowHeight, double x1, double y1, double x2, double y2) {
        Arrow arrow = new Arrow();
        arrow.setAngle(arrowAngle);
        arrow.setHeight(arrowHeight);
        return buildArrow(x1, y1, x2, y2, arrow);
    }

    /**
     * 获取箭头实体类
     *
     * @param x1    线的第一个点x
     * @param y1    线的第一个点y
     * @param x2    线的第二个点x
     * @param y2    线的第二个点y
     * @param arrow
     * @return
     */
    public static Arrow buildArrow(double x1, double y1, double x2, double y2, Arrow arrow) {
        double arrowAngle = arrow.getAngle();
        double arrowHeight = arrow.getHeight();

        // 计算斜边
        double hypotenuse = arrowHeight / Math.cos(Math.toRadians(arrowAngle / 2));

        // 计算当前线所在的象限
        int quadrant = -1;
        if (x1 > x2 && y1 < y2) {
            quadrant = 1;
        } else if (x1 < x2 && y1 < y2) {
            quadrant = 2;
        } else if (x1 < x2 && y1 > y2) {
            quadrant = 3;
        } else if (x1 > x2 && y1 > y2) {
            quadrant = 4;
        }

        // 计算线的夹角
        double linAngle = getLineAngle(x1, y1, x2, y2);
        if (Double.isNaN(linAngle)) {
            // 线与x轴垂直
            if (x1 == x2) {
                if (y1 < y2) {
                    linAngle = 90;
                } else {
                    linAngle = 270;
                }
                quadrant = 2;
            }
        }
        // 线与y轴垂直
        else if (linAngle == 0) {
            if (y1 == y2) {
                if (x1 < x2) {
                    linAngle = 0;
                } else {
                    linAngle = 180;
                }
                quadrant = 2;
            }
        }

        // 上侧一半箭头
        double xAngle = linAngle - arrowAngle / 2; // 与x轴夹角
        double py0 = hypotenuse * Math.sin(Math.toRadians(xAngle)); // 计算y方向增量
        double px0 = hypotenuse * Math.cos(Math.toRadians(xAngle)); // 计算x方向增量

        // 下侧一半箭头
        double yAngle = 90 - linAngle - arrowAngle / 2; // 与y轴夹角
        double px1 = hypotenuse * Math.sin(Math.toRadians(yAngle));
        double py1 = hypotenuse * Math.cos(Math.toRadians(yAngle));

        // 第一象限
        if (quadrant == 1) {
            px0 = -px0;
            px1 = -px1;

        } else if (quadrant == 2) {
            // do nothing
        } else if (quadrant == 3) {
            py0 = -py0;
            py1 = -py1;

        } else if (quadrant == 4) {
            py0 = -py0;
            px0 = -px0;

            px1 = -px1;
            py1 = -py1;
        }

        // build

        // move to point
        Point moveToPoint = new Point();
        moveToPoint.setX(x1);
        moveToPoint.setY(y1);
        arrow.setMoveToPoint(moveToPoint);

        // line to point list
        List<Point> lineToPointList = new ArrayList<>();
        Point lineToPoint = null;

        lineToPoint = new Point();
        lineToPoint.setX(x1 + px0);
        lineToPoint.setY(y1 + py0);
        lineToPointList.add(lineToPoint);

        lineToPoint = new Point();
        lineToPoint.setX(x1 + px1);
        lineToPoint.setY(y1 + py1);
        lineToPointList.add(lineToPoint);

        arrow.setLineToPointList(lineToPointList);
        return arrow;
    }


    /**
     * 获取线与X轴的夹角
     *
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    private static double getLineAngle(double x1, double y1, double x2, double y2) {
        double k1 = (y2 - y1) / (x2 - x1);
        double k2 = 0;
        return Math.abs(Math.toDegrees(Math.atan((k2 - k1) / (1 + k1 * k2))));
    }
}
