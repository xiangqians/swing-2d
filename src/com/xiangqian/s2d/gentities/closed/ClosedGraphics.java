package com.xiangqian.s2d.gentities.closed;

import com.xiangqian.s2d.gentities.Graphics;
import com.xiangqian.s2d.gentities.attr.RGB;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封闭图形
 *
 * @author xiangqian
 * @date 09:27 2019/11/03
 */
@Data
@NoArgsConstructor
public class ClosedGraphics extends Graphics {
    private int strokeType = 1; // 1，线类型；2，填充类型
    private float strokeWidth; // 线宽
    private RGB strokeRGB; // 颜色
}
