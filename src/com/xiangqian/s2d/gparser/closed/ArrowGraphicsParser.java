package com.xiangqian.s2d.gparser.closed;

import com.xiangqian.s2d.expand.component.ColorPlus;
import com.xiangqian.s2d.expand.component.StrokePlus;
import com.xiangqian.s2d.gentities.Graphics;
import com.xiangqian.s2d.gentities.closed.Arrow;
import com.xiangqian.s2d.gentities.unclosed.Point;
import com.xiangqian.s2d.gparser.entities.Graphics2DPainter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.geom.GeneralPath;

/**
 * @author xiangqian
 * @date 12:51 2019/12/14
 */
@Slf4j
public class ArrowGraphicsParser extends ClosedGraphicsParser {

    private Arrow arrow;
    private GeneralPath arrow2D;

    private StrokePlus stroke;
    private ColorPlus color;

    public ArrowGraphicsParser() {
        stroke = new StrokePlus();
        color = new ColorPlus(DEFAULT_RGB);

        arrow2D = new GeneralPath();
    }

    @Override
    public void setGraphics(Graphics graphics) {
        arrow = (Arrow) graphics;
    }

    @Override
    public void draw(Graphics2DPainter graphics2DPainter) {
        if (!check()) {
            return;
        }
        graphics2DPainter.reset();

        arrow2D.reset();
        arrow2D.moveTo(arrow.getMoveToPoint().getX(), arrow.getMoveToPoint().getY());
        Point line2Point = null;
        for (int i = 0, size = arrow.getLineToPointList().size(); i < size; i++) {
            line2Point = arrow.getLineToPointList().get(i);
            arrow2D.lineTo(line2Point.getX(), line2Point.getY());
        }
        arrow2D.closePath();

        Graphics2D g2d = graphics2DPainter.getGraphics2D();

        //
        stroke.setWidth(arrow.getStrokeWidth());
        g2d.setStroke(stroke);

        // color
        try {
            color.setRGB(arrow.getStrokeRGB());
            g2d.setColor(color);
        } catch (Exception e) {
            log.error("", e);
        }

        // draw
        // 线类型
        if (arrow.getStrokeType() == 1) {
            g2d.draw(arrow2D);
        }
        // 填充类型
        else if (arrow.getStrokeType() == 2) {
            g2d.fill(arrow2D);
        }

        graphics2DPainter.reset();
    }

    private boolean check() {
        if (arrow.getAngle() <= 0 || arrow.getHeight() <= 0) {
            return false;
        }

        if (arrow.getStrokeRGB() == null) {
            arrow.setStrokeRGB(DEFAULT_RGB);
        }

        return true;
    }


}
