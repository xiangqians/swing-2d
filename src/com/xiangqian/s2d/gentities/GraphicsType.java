package com.xiangqian.s2d.gentities;

/**
 * 图形类型
 *
 * @author xiangqian
 * @date 14:57 2019/10/27
 */
public enum GraphicsType {
    NO, // 没有任何图形

    // unclosed
    POINT, // 点
    LINE, // 线
    POLYLINE, // 折线

    // closed
    RECT, // 矩形
    IMAGE, // 图片
    TEXT, // 文本
    ELLIPSE, // 椭圆
    CIRCLE, // 圆
    POLYGON, // 多边形
    ARROW, // 箭头
}
