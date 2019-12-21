package com.xiangqian.s2d.container.drawpanel;

import com.xiangqian.s2d.gentities.Graphics;
import com.xiangqian.s2d.gentities.GraphicsFactory;
import com.xiangqian.s2d.gentities.unclosed.Point;
import com.xiangqian.s2d.gparser.GraphicsParser;
import com.xiangqian.s2d.gparser.GraphicsParserFactory;
import com.xiangqian.s2d.gparser.entities.Graphics2DPainter;
import com.xiangqian.s2d.gparser.event.EventAttributeFactory;
import com.xiangqian.s2d.shared.SharedPool;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

/**
 * 绘制面板
 *
 * @author xiangqian
 * @date 09:24 2019/11/09
 */
@Slf4j
public class DrawPanel extends javax.swing.JPanel {

    // 绘制图形
    @Getter
    @Setter
    private List<Graphics> drawGraphicsList;

    // 绘制面板当前所处的状态
    @Getter
    @Setter
    private State state;

    // 当前绘制的图形
    @Getter
    @Setter
    private Graphics curDrawGraphics;

    // 当前已选择的图形
    @Getter
    @Setter
    private Graphics curSelectedGraphics;

    @Getter
    private Graphics2DPainter graphics2DPainter;

    public DrawPanel() {
        init();
        SharedPool.get(SharedPool.DrawPanelShare.class).set(this);
    }

    private void init() {
        //
        this.drawGraphicsList = new LinkedList<>();
        this.state = State.DEFAULT;

        //
        graphics2DPainter = new Graphics2DPainter();
        graphics2DPainter.setDrawPanel(this);

        //
//        setPreferredSize(new java.awt.Dimension(ConfFactory.getInt("dp.width"), ConfFactory.getInt("dp.height")));
        setCursorStyle(CursorStyle.DEFAULT);

        // 设置绘制区的背景是白色
        setBackground(java.awt.Color.WHITE);

        // 添加监听事件
        CustomMouseListener customMouseListener = new CustomMouseListener(this);
        this.addMouseListener(customMouseListener);
        this.addMouseMotionListener(customMouseListener);
        this.addKeyListener(new CustomKeyListener(this));
    }

    /**
     * 设置绘制面板鼠标风格
     *
     * @param cursorStyle
     */
    public void setCursorStyle(CursorStyle cursorStyle) {
        switch (cursorStyle) {
            case DEFAULT:
                this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.DEFAULT_CURSOR));
                break;

            case HAND:
                this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.HAND_CURSOR));
                break;

            case CROSSHAIR:
                this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.CROSSHAIR_CURSOR));
                break;

            case MOVE:
                this.setCursor(java.awt.Cursor.getPredefinedCursor(java.awt.Cursor.MOVE_CURSOR));
                break;

            default:
                break;
        }
    }

    public void updateCursorStyle() {
        switch (state) {
            case DEFAULT:
                setCursorStyle(CursorStyle.DEFAULT);
                break;

            case DRAWING:
                setCursorStyle(CursorStyle.CROSSHAIR);
                break;

            case MODIFY:
                setCursorStyle(CursorStyle.DEFAULT);
                break;

            default:
                break;
        }
    }

    public void setCurDrawGraphics(Graphics curDrawGraphics) {
        if (curDrawGraphics == null) {
            this.state = State.DEFAULT;
        } else {
            this.state = State.DRAWING;
        }
        this.curDrawGraphics = curDrawGraphics;
        updateCursorStyle();
    }

    public void setCurSelectedGraphics(Graphics curSelectedGraphics) {
        if (curSelectedGraphics == null) {
            this.state = State.DEFAULT;
        } else {
            this.state = State.MODIFY;
        }
        this.curSelectedGraphics = curSelectedGraphics;
        updateCursorStyle();
    }

    public void pushCurDrawGraphics2list() {
        if (State.DRAWING == this.state) {
            drawGraphicsList.add(this.curDrawGraphics);

            // reset CurDrawGraphics
            this.curDrawGraphics = GraphicsFactory.getGraphics(this.curDrawGraphics.getType());
        }
    }


    public boolean removeGraphicsById(String graphicsId) {
        int size = drawGraphicsList.size();
        int index = -1;
        for (int i = 0; i < size; i++) {
            if (drawGraphicsList.get(i).getId().equals(graphicsId)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            drawGraphicsList.remove(index);
            return true;
        }

        return false;
    }


    @Override
    protected void paintComponent(java.awt.Graphics g) {
        if (drawGraphicsList == null) {
            log.warn("drawGraphicsList is null");
            return;
        }

        //
        graphics2DPainter.setGraphics2D((java.awt.Graphics2D) g);
        graphics2DPainter.setEventAttribute(EventAttributeFactory.get());

        //
        GraphicsParser graphicsParser = null;
        int size = drawGraphicsList.size();
        log.debug("state=" + state + ", size=" + size);
        for (int i = 0; i < size; i++) {
//            log.debug("[" + i + "] " + JSONUtils.toJson(drawGraphicsList.get(i)));
            graphicsParser = GraphicsParserFactory.getParser(drawGraphicsList.get(i));
            if (graphicsParser != null) {
                graphicsParser.draw(graphics2DPainter);
            }
        }

        //
        if (State.DRAWING == state) {
            graphicsParser = GraphicsParserFactory.getParser(this.curDrawGraphics);
            if (graphicsParser != null) {
                graphicsParser.draw(graphics2DPainter);
            }
        }
    }

    /**
     * 刷新绘图板
     */
    public void refresh() {
        SharedPool.get(SharedPool.MainFrameShare.class).get().repaint();
    }

    /**
     * 鼠标样式
     *
     * @author xiangqian
     * @date 09:23 2019/11/06
     */
    public static enum CursorStyle {
        DEFAULT, // 默认设置鼠标光标形状为箭头
        HAND, // 设置鼠标光标形状为手势光标
        CROSSHAIR, // 设置鼠标光标形状为十字线光标
        MOVE, // 拖动光标、移动光标
    }

    /**
     * 绘制面板状态
     *
     * @author xiangqian
     * @date 14:56 2019/10/28
     */
    public static enum State {
        DEFAULT, // 默认
        DRAWING, // 绘图
        MODIFY, // 修改
    }

}
