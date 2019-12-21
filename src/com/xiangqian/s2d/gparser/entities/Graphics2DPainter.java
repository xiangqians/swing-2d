package com.xiangqian.s2d.gparser.entities;

import com.xiangqian.s2d.conf.AttrConf;
import com.xiangqian.s2d.conf.ConfFactory;
import com.xiangqian.s2d.container.drawpanel.DrawPanel;
import com.xiangqian.s2d.expand.component.StrokePlus;
import com.xiangqian.s2d.gentities.attr.RGB;
import com.xiangqian.s2d.gentities.unclosed.Point;
import com.xiangqian.s2d.gparser.event.EventAttribute;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;

/**
 * Graphics2D图形绘制器
 *
 * @author xiangqian
 * @date 14:13 2019/11/30
 */
@Data
public class Graphics2DPainter {

    private DrawPanel drawPanel;
    private Graphics2D graphics2D;
    private EventAttribute eventAttribute;

    private StrokePlus defaultStroke;
    private Color defaultColor;

    public Graphics2DPainter() {
        AttrConf attrConf = ConfFactory.get(AttrConf.class);
        defaultStroke = new StrokePlus(attrConf.getStrokeWidth());

        RGB rgb = attrConf.getDefaultRgb();
        defaultColor = new Color(rgb.getR(), rgb.getG(), rgb.getB());
    }

    /**
     * 重新设置g2d样式
     */
    public void reset() {
        graphics2D.setStroke(defaultStroke);
        graphics2D.setColor(defaultColor);
    }

}
