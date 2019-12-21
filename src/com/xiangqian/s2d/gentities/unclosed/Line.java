package com.xiangqian.s2d.gentities.unclosed;

import com.xiangqian.s2d.gentities.GraphicsType;
import lombok.Data;

/**
 * @author xiangqian
 * @date 14:57 2019/10/27
 */
@Data
public class Line extends UnclosedGraphics {

    private Point point1;
    private Point point2;

    public Line() {
        setType(GraphicsType.LINE);
    }
}
