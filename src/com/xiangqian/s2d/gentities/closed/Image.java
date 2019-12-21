package com.xiangqian.s2d.gentities.closed;

import com.xiangqian.s2d.gentities.GraphicsType;
import lombok.Data;

/**
 * @author xiangqian
 * @date 14:57 2019/10/27
 */
@Data
public class Image extends Rect {

    private String src; // 图片资源

    public Image() {
        setType(GraphicsType.IMAGE);
    }
}
