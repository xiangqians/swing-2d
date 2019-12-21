package com.xiangqian.s2d.gentities.closed;

import com.xiangqian.s2d.gentities.GraphicsType;
import com.xiangqian.s2d.gentities.attr.Font;
import lombok.Data;

/**
 * @author xiangqian
 * @date 14:58 2019/10/27
 */
@Data
public class Text extends Rect {

    // 文本占据矩形区域

    private String text; // 文本
    private Font font; // 字体

    public Text() {
        setType(GraphicsType.TEXT);
    }

}
