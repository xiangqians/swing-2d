package com.xiangqian.s2d.container.drawpanel;

import com.xiangqian.s2d.gentities.Graphics;
import com.xiangqian.s2d.gparser.GraphicsParser;
import com.xiangqian.s2d.gparser.GraphicsParserFactory;
import com.xiangqian.s2d.gparser.event.Event;
import com.xiangqian.s2d.gparser.event.EventAttribute;
import com.xiangqian.s2d.gparser.event.EventAttributeFactory;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * 鼠标事件监听
 *
 * @author xiangqian
 * @date 14:54 2019/10/27
 */
@Slf4j
public class CustomMouseListener extends MouseAdapter {

    private DrawPanel drawPanel;

    public CustomMouseListener(DrawPanel drawPanel) {
        this.drawPanel = drawPanel;
    }


    // 鼠标移动刷新标志位。仅仅使用低八位标识：0000 0000
    private int refreshFlagOnMouseMoved;

    /**
     * 触发鼠标移动事件
     * 记录刷新前后刷新值，防止无用刷新
     *
     * @param flag
     */
    private void triggerMouseMoved(boolean flag) {

        // 低四位（l4）和高四位（h4）
        int l4 = 0, h4 = 0;

        // set
        l4 = refreshFlagOnMouseMoved & 15;
        refreshFlagOnMouseMoved = l4 << 4;
        int number = flag ? 1 : 0;
        refreshFlagOnMouseMoved = refreshFlagOnMouseMoved | number;

        // refresh
        h4 = h4 = refreshFlagOnMouseMoved >> 4;
        l4 = refreshFlagOnMouseMoved & 15;
        if ((l4 == 1 && l4 != h4) || h4 != l4) {
            drawPanel.refresh();
        }
    }

    /**
     * 鼠标移动事件监听
     *
     * @param e
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        EventAttribute eventAttribute = EventAttributeFactory.getAndRefresh(e);
        DrawPanel.State state = drawPanel.getState();

        // 检测绘制图形集与事件位置是否发生相交
        if (DrawPanel.State.DEFAULT == state) {
            detectIntersects(eventAttribute);

        }
        // 触发当前绘制图形事件
        else if (DrawPanel.State.DRAWING == state) {
            triggerCurDrawGraphicsEvent(Event.MOUSE_MOVED, eventAttribute);

        }
        // 检测绘制图形集与事件位置是否发生相交
        else if (DrawPanel.State.MODIFY == state) {
            detectIntersects(eventAttribute);
        }

        // 消费事件
        e.consume();
    }


    /**
     * 鼠标进入事件监听
     *
     * @param e
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        // Nothing to do
    }


    /**
     * 鼠标按下事件监听
     *
     * @param e
     */
    @Override
    public void mousePressed(MouseEvent e) {
        EventAttribute eventAttribute = EventAttributeFactory.getAndRefresh(e);
        DrawPanel.State state = drawPanel.getState();

        // 检测并触发图形集事件
        if (DrawPanel.State.DEFAULT == state) {
            detectTriggerEvent(Event.MOUSE_PRESSED, eventAttribute);

        }
        // 触发当前绘制图形事件
        else if (DrawPanel.State.DRAWING == state) {
            triggerCurDrawGraphicsEvent(Event.MOUSE_PRESSED, eventAttribute);

        }
        //
        else if (DrawPanel.State.MODIFY == state) {
            // 触发当前选中图形事件
            if (!triggerCurSelectedGraphicsEvent(Event.MOUSE_PRESSED, eventAttribute)) {
                // 检测并触发图形集事件
                if (!detectTriggerEvent(Event.MOUSE_PRESSED, eventAttribute)) {
                    // 没有任何触发图形，则设置绘制面板当前选中图形为null
                    drawPanel.setCurSelectedGraphics(null);

                    // 刷新面板
                    drawPanel.refresh();
                }
            }
        }

        e.consume();
    }


    /**
     * 鼠标拖动事件监听
     *
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        EventAttribute eventAttribute = EventAttributeFactory.getAndRefresh(e);
        DrawPanel.State state = drawPanel.getState();
        if (DrawPanel.State.DEFAULT == state) {
            // Nothing to do

        }
        // 触发当前绘制图形事件
        else if (DrawPanel.State.DRAWING == state) {
            triggerCurDrawGraphicsEvent(Event.MOUSE_DRAGGED, eventAttribute);

        }
        //
        else if (DrawPanel.State.MODIFY == state) {
            // 触发当前选中图形事件
            if (!triggerCurSelectedGraphicsEvent(Event.MOUSE_DRAGGED, eventAttribute)) {
                // 检测并触发图形集事件
                detectTriggerEvent(Event.MOUSE_DRAGGED, eventAttribute);
            }
        }

        e.consume();
    }


    /**
     * 鼠标释放事件监听
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        EventAttribute eventAttribute = EventAttributeFactory.getAndRefresh(e);
        DrawPanel.State state = drawPanel.getState();
        if (DrawPanel.State.DEFAULT == state) {
            // Nothing to do

        }
        // 触发当前绘制图形事件
        else if (DrawPanel.State.DRAWING == state) {
            triggerCurDrawGraphicsEvent(Event.MOUSE_RELEASED, eventAttribute);

        }
        // 触发当前选中图形事件
        else if (DrawPanel.State.MODIFY == state) {
            triggerCurSelectedGraphicsEvent(Event.MOUSE_RELEASED, eventAttribute);
        }

        e.consume();
    }

    /**
     * 鼠标点击事件监听
     *
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // Nothing to do
    }


    /**
     * 鼠标退出监听
     *
     * @param e
     */
    @Override
    public void mouseExited(MouseEvent e) {
        // Nothing to do
    }


    /**
     * 检测绘制图形集与事件位置是否发生相交
     *
     * @param eventAttribute
     * @return
     */
    private boolean detectIntersects(EventAttribute eventAttribute) {
        List<Graphics> drawGraphicsList = drawPanel.getDrawGraphicsList();
        if (drawGraphicsList.isEmpty()) {
            return false;
        }

        int intersectsType = -1;

        GraphicsParser graphicsParser = null;
        for (int i = 0, size = drawGraphicsList.size(); i < size; i++) {
            // 获取图形解析器
            graphicsParser = GraphicsParserFactory.getParser(drawGraphicsList.get(i));

            // 检测(x, y)坐标是否相交与图形
            if (graphicsParser != null && (intersectsType = graphicsParser.intersects(eventAttribute.getCurEventPoint().getX(), eventAttribute.getCurEventPoint().getY())) != -1) {
                break;
            }
        }

        // 不相交
        if (intersectsType == -1) {
            drawPanel.updateCursorStyle();

        }
        // 相交于图形体
        else if (intersectsType == 0) {
            drawPanel.setCursorStyle(DrawPanel.CursorStyle.MOVE);

        }
        // 相交于拉伸端点
        else if (intersectsType >= 1) {
            drawPanel.setCursorStyle(DrawPanel.CursorStyle.HAND);
        }

        // 触发鼠标移动事件
        triggerMouseMoved(intersectsType != -1);

        return intersectsType != -1;
    }


    /**
     * 检测并触发图形集事件
     *
     * @param event
     * @param eventAttribute
     * @return
     */
    private boolean detectTriggerEvent(Event event, EventAttribute eventAttribute) {
        List<Graphics> drawGraphicsList = drawPanel.getDrawGraphicsList();
        if (drawGraphicsList.isEmpty()) {
            return false;
        }

        boolean flag = false;
        GraphicsParser graphicsParser = null;
        Graphics graphics = null;
        for (int i = 0, size = drawGraphicsList.size(); i < size; i++) {
            graphics = drawGraphicsList.get(i);

            // 获取图形解析器
            graphicsParser = GraphicsParserFactory.getParser(graphics);
            if (graphicsParser != null) {
                // 事件属性预处理
                eventAttribute.postProcessBefore(graphics);

                // 触发事件
                flag = graphicsParser.triggerEvent(event, eventAttribute);
                if (flag) {
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 触发当前绘制图形事件
     *
     * @param event
     * @param eventAttribute
     * @return
     */
    private boolean triggerCurDrawGraphicsEvent(Event event, EventAttribute eventAttribute) {
        boolean flag = triggerEvent(event, eventAttribute, drawPanel.getCurDrawGraphics());

        // 刷新绘制面板
        if (flag) {
            drawPanel.refresh();
        }

        // 请求焦点
        drawPanel.requestFocus();

        return flag;

    }

    /**
     * 触发当前选中图形事件
     *
     * @param event
     * @param eventAttribute
     * @return
     */
    private boolean triggerCurSelectedGraphicsEvent(Event event, EventAttribute eventAttribute) {
        boolean flag = triggerEvent(event, eventAttribute, drawPanel.getCurSelectedGraphics());

        if (flag) {
            drawPanel.refresh();
        }

        // 请求焦点
        drawPanel.requestFocus();

        return flag;
    }

    /**
     * 触发事件
     *
     * @param event          事件
     * @param eventAttribute 事件属性
     * @param graphics       将要触发的图形
     * @return
     */
    private boolean triggerEvent(Event event, EventAttribute eventAttribute, Graphics graphics) {
        // 获取图形解析器
        GraphicsParser graphicsParser = GraphicsParserFactory.getParser(graphics);
        if (graphicsParser == null) {
            return false;
        }

        eventAttribute.postProcessBefore(graphics);

        // 触发事件
        return graphicsParser.triggerEvent(event, eventAttribute);
    }

}
