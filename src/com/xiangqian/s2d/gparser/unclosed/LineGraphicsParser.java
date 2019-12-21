package com.xiangqian.s2d.gparser.unclosed;

import com.xiangqian.s2d.expand.container.top.PopUpWindow;
import com.xiangqian.s2d.gentities.Graphics;
import com.xiangqian.s2d.gentities.attr.RGB;
import com.xiangqian.s2d.gentities.closed.Arrow;
import com.xiangqian.s2d.gentities.unclosed.Line;
import com.xiangqian.s2d.gentities.unclosed.Point;
import com.xiangqian.s2d.gparser.GraphicsParser;
import com.xiangqian.s2d.gparser.GraphicsParserFactory;
import com.xiangqian.s2d.gparser.entities.Graphics2DPainter;
import com.xiangqian.s2d.gparser.entities.VerifyIntersectShape;
import com.xiangqian.s2d.gparser.event.EventAdapter;
import com.xiangqian.s2d.gparser.event.EventAttribute;
import com.xiangqian.s2d.gparser.event.EventListener;
import com.xiangqian.s2d.gparser.event.MouseButton;
import com.xiangqian.s2d.util.ArrowUtils;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiangqian
 * @date 09:35 2019/11/09
 */
@Slf4j
public class LineGraphicsParser extends UnclosedGraphicsParser {

    private Line line;
    private Line2D.Double line2D;

    // 移动点
    private Ellipse2D.Double movePoint1;
    private Ellipse2D.Double movePoint2;

    // 校验交叉图形
    private List<VerifyIntersectShape> verifyIntersectShapeList;

    public LineGraphicsParser() {
        this.line2D = new Line2D.Double();
        this.movePoint1 = new Ellipse2D.Double();
        this.movePoint2 = new Ellipse2D.Double();

        //
        verifyIntersectShapeList = new ArrayList<>(3);
    }

    private EventListener defaultEventListener = new EventAdapter() {
        @Override
        public boolean mousePressed(EventAttribute eventAttribute) {
            if (intersects(eventAttribute) == -1) {
                return false;
            }

            eventAttribute.getDrawPanel().setCurSelectedGraphics(line);

            //
            updateDPCSByIntersectsType();

            return true;
        }
    };

    private EventListener drawingEventListener = new EventAdapter() {
        @Override
        public boolean mousePressed(EventAttribute eventAttribute) {
            if (MouseButton.LEFT_BTN != eventAttribute.getMouseButton()) {
                return false;
            }

            Point point1 = line.getPoint1();
            point1.setX(eventAttribute.getCurEventPoint().getX());
            point1.setY(eventAttribute.getCurEventPoint().getY());

            Point point2 = line.getPoint2();
            point2.setX(eventAttribute.getCurEventPoint().getX());
            point2.setY(eventAttribute.getCurEventPoint().getY());

            return true;
        }

        @Override
        public boolean mouseDragged(EventAttribute eventAttribute) {
            Point point2 = line.getPoint2();
            point2.setX(eventAttribute.getCurEventPoint().getX());
            point2.setY(eventAttribute.getCurEventPoint().getY());
            return true;
        }

        @Override
        public boolean mouseReleased(EventAttribute eventAttribute) {
            Point point1 = line.getPoint1();
            Point point2 = line.getPoint2();
            if (point1.getX() == point2.getX() && point1.getY() == point2.getY()) {
                return false;
            }

            eventAttribute.getDrawPanel().pushCurDrawGraphics2list();
            return true;
        }
    };

    private EventListener modifyEventListener = new EventAdapter() {
        @Override
        public boolean mousePressed(EventAttribute eventAttribute) {
            if (intersects(eventAttribute) == -1) {
                return false;
            }

            eventAttribute.getDrawPanel().setCurSelectedGraphics(line);

            //
            updateDPCSByIntersectsType();

            if (isDoubleClick(eventAttribute)) {
                PopUpWindow.graphicsModification(line);
            }

            return true;
        }

        @Override
        public boolean mouseDragged(EventAttribute eventAttribute) {
            Graphics curSelectedGraphics = null;
            if (eventAttribute.getIntersectsType() == -1
                    || (curSelectedGraphics = eventAttribute.getDrawPanel().getCurSelectedGraphics()) == null
                    || !line.getId().equals(curSelectedGraphics.getId())) {
                return false;
            }

            edit(eventAttribute);
            return true;
        }

        @Override
        public boolean mouseReleased(EventAttribute eventAttribute) {
            Graphics curSelectedGraphics = null;
            if ((curSelectedGraphics = eventAttribute.getDrawPanel().getCurSelectedGraphics()) == null
                    || !line.getId().equals(curSelectedGraphics.getId())) {
                return false;
            }

            return true;
        }


    };

    @Override
    protected boolean load() {

        Point point1 = line.getPoint1();
        Point point2 = line.getPoint2();

        //
        line2D.x1 = point1.getX();
        line2D.y1 = point1.getY();
        line2D.x2 = point2.getX();
        line2D.y2 = point2.getY();

        //
        initMovePoint(movePoint1, point1.getX(), point1.getY());
        initMovePoint(movePoint2, point2.getX(), point2.getY());

        //
        verifyIntersectShapeList.clear();
        verifyIntersectShapeList.add(new VerifyIntersectShape(0, line2D));
        verifyIntersectShapeList.add(new VerifyIntersectShape(1, movePoint1));
        verifyIntersectShapeList.add(new VerifyIntersectShape(2, movePoint2));

        return true;
    }

    private void initMovePoint(Ellipse2D.Double movePoint, double x, double y) {
        movePoint.setFrameFromCenter(x, y, x + pointRadius, y + pointRadius);
    }

    @Override
    protected void setGraphics0(Graphics graphics) {
        this.line = (Line) graphics;
    }

    @Override
    protected EventListener defaultEventListener() {
        return defaultEventListener;
    }

    @Override
    protected EventListener drawingEventListener() {
        return drawingEventListener;
    }

    @Override
    protected EventListener modifyEventListener() {
        return modifyEventListener;
    }

    @Override
    protected List<VerifyIntersectShape> getVerifyIntersectShapeList() {
        return verifyIntersectShapeList;
    }

    @Override
    protected void draw0(Graphics2DPainter graphics2DPainter) {
        graphics2DPainter.getGraphics2D().draw(line2D);
    }

    @Override
    protected void drawEditPosition(Graphics2DPainter graphics2DPainter) {
        Graphics2D g2d = graphics2DPainter.getGraphics2D();
        g2d.fill(movePoint1);
        g2d.fill(movePoint2);
    }

    @Override
    protected void drawArrow(Graphics2DPainter graphics2DPainter) {
        int arrowType = line.getArrowType();
        switch (arrowType) {
            case -1:
                // Nothing to do
                break;
            case 3:
            case 1:
                drawArrow(graphics2DPainter, line.getPoint1(), line.getPoint2(), line.getArrow1());
                if (arrowType == 1) {
                    break;
                }

            case 2:
                drawArrow(graphics2DPainter, line.getPoint2(), line.getPoint1(), line.getArrow2());
                if (arrowType == 2) {
                    break;
                }

            default:
                break;
        }
    }

    @Override
    public boolean edit(EventAttribute eventAttribute) {
        if (eventAttribute.getIntersectsType() == 0) {

            // 计算增量值
            double[] incrementXY = getIncrement(
                    eventAttribute.getPreEventPoint().getX(),
                    eventAttribute.getPreEventPoint().getY(),
                    eventAttribute.getCurEventPoint().getX(),
                    eventAttribute.getCurEventPoint().getY());
            double incrementX = incrementXY[0];
            double incrementY = incrementXY[1];

            Point point1 = line.getPoint1();
            Point point2 = line.getPoint2();

            // set
            point1.setX(point1.getX() + incrementX);
            point2.setX(point2.getX() + incrementX);
            point1.setY(point1.getY() - incrementY);
            point2.setY(point2.getY() - incrementY);

        } else if (eventAttribute.getIntersectsType() == 1) {
            Point point1 = line.getPoint1();
            point1.setX(eventAttribute.getCurEventPoint().getX());
            point1.setY(eventAttribute.getCurEventPoint().getY());

        } else if (eventAttribute.getIntersectsType() == 2) {
            Point point2 = line.getPoint2();
            point2.setX(eventAttribute.getCurEventPoint().getX());
            point2.setY(eventAttribute.getCurEventPoint().getY());
        }

        updateDPCSByIntersectsType();
        return true;
    }

}
