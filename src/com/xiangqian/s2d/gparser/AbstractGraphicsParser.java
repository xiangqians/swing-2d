package com.xiangqian.s2d.gparser;

import com.xiangqian.s2d.conf.AttrConf;
import com.xiangqian.s2d.conf.ConfFactory;
import com.xiangqian.s2d.container.drawpanel.DrawPanel;
import com.xiangqian.s2d.gentities.Graphics;
import com.xiangqian.s2d.gentities.attr.RGB;
import com.xiangqian.s2d.gentities.unclosed.Point;
import com.xiangqian.s2d.gparser.entities.VerifyIntersectShape;
import com.xiangqian.s2d.gparser.event.Event;
import com.xiangqian.s2d.gparser.event.EventAttribute;
import com.xiangqian.s2d.gparser.event.EventListener;
import com.xiangqian.s2d.shared.SharedPool;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.List;

/**
 * @author xiangqian
 * @date 11:52 2019/11/30
 */
@Slf4j
public abstract class AbstractGraphicsParser implements GraphicsParser {

    // 相交类型
    protected int intersectsType;

    // 设置图形
    public abstract void setGraphics(Graphics graphics);

    // 默认的事件监听
    protected abstract EventListener defaultEventListener();

    // 绘制事件监听
    protected abstract EventListener drawingEventListener();

    // 修改事件监听
    protected abstract EventListener modifyEventListener();

    @Override
    public boolean triggerEvent(Event event, EventAttribute eventAttribute) {
        EventListener eventListener = null;
        DrawPanel.State state = eventAttribute.getDrawPanel().getState();
        if (DrawPanel.State.DEFAULT == state) {
            eventListener = defaultEventListener();

        } else if (DrawPanel.State.DRAWING == state) {
            eventListener = drawingEventListener();

        } else if (DrawPanel.State.MODIFY == state) {
            eventListener = modifyEventListener();
        }

        if (eventListener == null) {
            return false;
        }

        boolean flag = false;
        switch (event) {
            case MOUSE_MOVED:
                flag = eventListener.mouseMoved(eventAttribute);
                break;
            case MOUSE_ENTERED:
                flag = eventListener.mouseEntered(eventAttribute);
                break;
            case MOUSE_PRESSED:
                flag = eventListener.mousePressed(eventAttribute);
                break;
            case MOUSE_DRAGGED:
                flag = eventListener.mouseDragged(eventAttribute);
                break;
            case MOUSE_RELEASED:
                flag = eventListener.mouseReleased(eventAttribute);
                break;
            case MOUSE_CLICKED:
                flag = eventListener.mouseClicked(eventAttribute);
                break;
            case MOUSE_EXITED:
                flag = eventListener.mouseExited(eventAttribute);
                break;
            default:
                break;
        }

        return flag;
    }

    protected double[] getIncrement(double previousPointX, double previousPointY, double currentPointX, double currentPointY) {
        double incrementX = currentPointX - previousPointX;
        double incrementY = currentPointY - previousPointY;

        // right
        if (currentPointX > previousPointX) {
            if (incrementX < 0) {
                incrementX = -incrementX;
            }
        }
        // left
        else {
            if (incrementX > 0) {
                incrementX = -incrementX;
            }
        }

        // up
        if (currentPointY > previousPointY) {
            if (incrementY > 0) {
                incrementY = -incrementY;
            }
        }
        // down
        else {
            if (incrementY < 0) {
                incrementY = -incrementY;
            }
        }

        return new double[]{incrementX, incrementY};
    }

    protected double[] getIncrement2(double previousPointX, double previousPointY, double currentPointX, double currentPointY) {
        double incrementX = currentPointX - previousPointX;
        double incrementY = currentPointY - previousPointY;
        return new double[]{incrementX, incrementY};
    }

    /**
     * updateDrawPanelCursorStyleByIntersectsType
     */
    protected void updateDPCSByIntersectsType() {
        DrawPanel drawPanel = SharedPool.get(SharedPool.DrawPanelShare.class).get();
        if (intersectsType == -1) {
            drawPanel.updateCursorStyle();
        } else if (intersectsType == 0) {
            drawPanel.setCursorStyle(DrawPanel.CursorStyle.MOVE);
        } else if (intersectsType >= 1) {
            drawPanel.setCursorStyle(DrawPanel.CursorStyle.HAND);
        }
    }

    protected abstract List<VerifyIntersectShape> getVerifyIntersectShapeList();

    protected int intersects(Point point) {
        return intersects(point.getX(), point.getY());
    }

    protected int intersects(EventAttribute eventAttribute) {
        intersects(eventAttribute.getCurEventPoint().getX(), eventAttribute.getCurEventPoint().getY());
        eventAttribute.setIntersectsType(intersectsType);
        return intersectsType;
    }

    @Override
    public int intersects(double x, double y) {
        intersectsType = -1;
        List<VerifyIntersectShape> verifyIntersectShapeList = getVerifyIntersectShapeList();
        if (verifyIntersectShapeList == null) {
            return intersectsType;
        }

        VerifyIntersectShape verifyIntersectShape = null;
        for (int i = 0, size = verifyIntersectShapeList.size(); i < size; i++) {
            verifyIntersectShape = verifyIntersectShapeList.get(i);
            if (verifyIntersectShape == null) {
                continue;
            }
            if (verifyIntersectShape.getShape().intersects(x, y, 10, 10)) {
                intersectsType = verifyIntersectShape.getIntersectsType();
                return intersectsType;
            }
        }
        return intersectsType;
    }

    /**
     * 是否是双击
     *
     * @param graphics       当前触发的图形
     * @param eventAttribute 事件属性
     * @param intervalTime   时间间隔
     * @return
     */
    protected boolean isDoubleClick(Graphics graphics, EventAttribute eventAttribute, long intervalTime) {
        long preTime = eventAttribute.getStoreTimeMillis();
        long curTime = System.currentTimeMillis();
        if (graphics.getId().equals(eventAttribute.getId()) && curTime - preTime < intervalTime) {
            return true;
        }
        eventAttribute.setId(graphics.getId());
        eventAttribute.setStoreTimeMillis(System.currentTimeMillis());
        return false;
    }

    protected boolean isDoubleClick(Graphics graphics, EventAttribute eventAttribute) {
        return isDoubleClick(graphics, eventAttribute, 300);
    }


    // default stroke
    public static final BasicStroke DEFAULT_LINE_STROKE;

    // 虚线Stroke
    public static final BasicStroke DOTTED_LINE_STROKE1;
    public static final BasicStroke DOTTED_LINE_STROKE2;

    public static final RGB DEFAULT_RGB;

    // color
    public static final Color DEFAULT_LINE_COLOR;
    public static final Color ARRIVE_LINE_COLOR;


    static {
        AttrConf attrConf = ConfFactory.get(AttrConf.class);

        DEFAULT_LINE_STROKE = new BasicStroke(attrConf.getStrokeWidth());

        DOTTED_LINE_STROKE1 = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1.0f, new float[]{4.0f, 2.0f}, 0);
        DOTTED_LINE_STROKE2 = new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 1.0f, new float[]{5.0f, 3.0f, 2.0f, 3.0f}, 0);

        DEFAULT_RGB = attrConf.getDefaultRgb();
        DEFAULT_LINE_COLOR = new Color(DEFAULT_RGB.getR(), DEFAULT_RGB.getG(), DEFAULT_RGB.getB());
        RGB arriveRgb = attrConf.getArriveRgb();
        ARRIVE_LINE_COLOR = new Color(arriveRgb.getR(), arriveRgb.getG(), arriveRgb.getB());
    }

}
