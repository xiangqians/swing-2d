package com.xiangqian.s2d.gparser.unclosed;

import com.xiangqian.s2d.conf.AttrConf;
import com.xiangqian.s2d.conf.ConfFactory;
import com.xiangqian.s2d.container.drawpanel.DrawPanel;
import com.xiangqian.s2d.gentities.Graphics;
import com.xiangqian.s2d.gentities.attr.RGB;
import com.xiangqian.s2d.gentities.closed.Arrow;
import com.xiangqian.s2d.gentities.unclosed.Point;
import com.xiangqian.s2d.gentities.unclosed.UnclosedGraphics;
import com.xiangqian.s2d.gparser.AbstractGraphicsParser;
import com.xiangqian.s2d.gparser.GraphicsParser;
import com.xiangqian.s2d.gparser.GraphicsParserFactory;
import com.xiangqian.s2d.gparser.entities.Graphics2DPainter;
import com.xiangqian.s2d.gparser.event.EventAttribute;
import com.xiangqian.s2d.expand.component.StrokePlus;
import com.xiangqian.s2d.util.ArrowUtils;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

/**
 * 未封闭图形
 *
 * @author xiangqian
 * @date 09:36 2019/11/09
 */
@Slf4j
public abstract class UnclosedGraphicsParser extends AbstractGraphicsParser {

    private UnclosedGraphics unclosedGraphics;

    protected StrokePlus stroke; // 线宽
    protected double pointRadius; // 点半径

    public UnclosedGraphicsParser() {
        this.pointRadius = 6d;
        this.stroke = new StrokePlus();
    }

    // 设置子类图形
    protected abstract void setGraphics0(Graphics graphics);

    @Override
    public void setGraphics(Graphics graphics) {
        this.unclosedGraphics = (UnclosedGraphics) graphics;

        // 初始化默认值
        if (unclosedGraphics.getStrokeWidth() < 1) {
            unclosedGraphics.setStrokeWidth(ConfFactory.get(AttrConf.class).getStrokeWidth());
        }
        if (unclosedGraphics.getStrokeRGB() == null) {
            unclosedGraphics.setStrokeRGB(ConfFactory.get(AttrConf.class).getDefaultRgb().clone());
        }

        //
        setGraphics0(graphics);

        // 加载
        load();
    }


    // 加载
    protected abstract boolean load();

    // 绘制图形
    protected abstract void draw0(Graphics2DPainter graphics2DPainter);

    // 绘制编辑位
    protected abstract void drawEditPosition(Graphics2DPainter graphics2DPainter);

    // 绘制箭头
    protected abstract void drawArrow(Graphics2DPainter graphics2DPainter);

    @Override
    public void draw(Graphics2DPainter graphics2DPainter) {
        if (!load()) {
            return;
        }
        graphics2DPainter.reset();

        // 设置样式
        setStyle(graphics2DPainter);

        // 绘制图形
        draw0(graphics2DPainter);

        DrawPanel.State state = graphics2DPainter.getDrawPanel().getState();
        if (DrawPanel.State.DEFAULT == state) {
            if (intersects(graphics2DPainter.getEventAttribute().getCurEventPoint()) != -1) {
                arrive(graphics2DPainter);
            }

        } else if (DrawPanel.State.DRAWING == state) {
            // Nothing to do

        } else if (DrawPanel.State.MODIFY == state) {
            if (!selected(graphics2DPainter)) {
                if (intersects(graphics2DPainter.getEventAttribute().getCurEventPoint()) != -1) {
                    arrive(graphics2DPainter);
                }
            }
        }

        drawArrow(graphics2DPainter);
        graphics2DPainter.reset();
    }

    private void setStyle(Graphics2DPainter graphics2DPainter) {
        Graphics2D g2d = graphics2DPainter.getGraphics2D();

        // stroke
        stroke.setWidth(unclosedGraphics.getStrokeWidth());
        g2d.setStroke(stroke);

        // color
        RGB rgb = unclosedGraphics.getStrokeRGB();
        g2d.setColor(new Color(rgb.getR(), rgb.getG(), rgb.getB()));
    }


    /**
     * 选中图形
     *
     * @param graphics2DPainter
     * @return
     */
    private boolean selected(Graphics2DPainter graphics2DPainter) {
        Graphics curSelectedGraphics = graphics2DPainter.getDrawPanel().getCurSelectedGraphics();
        if (curSelectedGraphics != null && unclosedGraphics.getId().equals(curSelectedGraphics.getId())) {
            arrive(graphics2DPainter);
            drawEditPosition(graphics2DPainter);
            return true;
        }

        return false;
    }

    /**
     * 到达
     *
     * @param graphics2DPainter
     */
    private boolean arrive(Graphics2DPainter graphics2DPainter) {
        Graphics2D g2d = graphics2DPainter.getGraphics2D();
        g2d.setStroke(DOTTED_LINE_STROKE1);
        g2d.setColor(ARRIVE_LINE_COLOR);
        draw0(graphics2DPainter);
        return true;
    }

    protected void drawArrow(Graphics2DPainter graphics2DPainter, Point point1, Point point2, Arrow arrow) {
        if (arrow == null) {
            return;
        }
        GraphicsParser graphicsParser = GraphicsParserFactory.getParser(ArrowUtils.buildArrow(point1, point2, arrow));
        if (graphicsParser != null) {
            graphicsParser.draw(graphics2DPainter);
        }
    }


    protected boolean isDoubleClick(EventAttribute eventAttribute) {
        return isDoubleClick(unclosedGraphics, eventAttribute);
    }

}
