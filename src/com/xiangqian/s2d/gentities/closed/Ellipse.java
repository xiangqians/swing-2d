package com.xiangqian.s2d.gentities.closed;

import com.xiangqian.s2d.gentities.GraphicsType;
import lombok.Data;

/**
 * 椭圆
 *
 * @author xiangqian
 * @date 14:57 2019/10/27
 */
@Data
public class Ellipse extends Rect {

    // 椭圆外接矩形

    public Ellipse() {
        setType(GraphicsType.ELLIPSE);
    }

}
