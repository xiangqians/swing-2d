package com.xiangqian.s2d.gentities.closed;

import com.xiangqian.s2d.gentities.GraphicsType;
import lombok.Data;

/**
 * @author xiangqian
 * @date 14:58 2019/10/27
 */
@Data
public class Rect extends ClosedGraphics {

    private double x; // 矩形起始点x轴坐标值
    private double y;// 矩形起始点y轴坐标值
    private double width;
    private double height;

    public Rect() {
        setType(GraphicsType.RECT);
    }
}
