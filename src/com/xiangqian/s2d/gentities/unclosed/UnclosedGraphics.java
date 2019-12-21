package com.xiangqian.s2d.gentities.unclosed;

import com.xiangqian.s2d.gentities.Graphics;
import com.xiangqian.s2d.gentities.attr.RGB;
import com.xiangqian.s2d.gentities.closed.Arrow;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 非封闭图形
 *
 * @author xiangqian
 * @date 09:27 2019/11/03
 */
@Data
@NoArgsConstructor
public class UnclosedGraphics extends Graphics {

    // stroke
    private float strokeWidth; // 线宽
    private RGB strokeRGB; // 线颜色

    // arrow
    private int arrowType = -1; // 箭头类型，-1，没有箭头；3，两箭头；1，（x1，y1）处有箭头；2，（x2，y2）处有箭头
    private Arrow arrow1; // 箭头1
    private Arrow arrow2; // 箭头2
}
