package com.xiangqian.s2d.gparser.unclosed;


import com.xiangqian.s2d.expand.container.top.PopUpWindow;
import com.xiangqian.s2d.gentities.Graphics;
import com.xiangqian.s2d.gentities.unclosed.Point;
import com.xiangqian.s2d.gentities.unclosed.Polyline;
import com.xiangqian.s2d.gparser.entities.Graphics2DPainter;
import com.xiangqian.s2d.gparser.entities.VerifyIntersectShape;
import com.xiangqian.s2d.gparser.event.EventAdapter;
import com.xiangqian.s2d.gparser.event.EventAttribute;
import com.xiangqian.s2d.gparser.event.EventListener;
import com.xiangqian.s2d.gparser.event.MouseButton;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;
import java.util.List;

/**
 * 折线
 *
 * @author xiangqian
 * @date 15:34 2019/11/09
 */
@Slf4j
public class PolylineGraphicsParser extends UnclosedGraphicsParser {

    private Polyline polyline;
    private GeneralPath polyline2D;

    // 校验交叉图形
    private List<VerifyIntersectShape> verifyIntersectShapeList;

    public PolylineGraphicsParser() {
        this.polyline2D = new GeneralPath();

        this.verifyIntersectShapeList = new ArrayList<>(6);
    }

    private EventListener defaultEventListener = new EventAdapter() {
        @Override
        public boolean mousePressed(EventAttribute eventAttribute) {
            if (intersects(eventAttribute) == -1) {
                return false;
            }

            eventAttribute.getDrawPanel().setCurSelectedGraphics(polyline);

            //
            updateDPCSByIntersectsType();

            return true;
        }
    };

    private EventListener drawingEventListener = new EventAdapter() {
        @Override
        public boolean mousePressed(EventAttribute eventAttribute) {
            // 左键
            if (MouseButton.LEFT_BTN == eventAttribute.getMouseButton()) {
                // move to
                if (polyline.getMoveToPoint() == null) {
                    Point moveToPoint = new Point();
                    moveToPoint.setX(eventAttribute.getCurEventPoint().getX());
                    moveToPoint.setY(eventAttribute.getCurEventPoint().getY());
                    polyline.setMoveToPoint(moveToPoint);

                    polyline.setLineToPointList(new ArrayList<>());
                    polyline.getLineToPointList().add(new Point(polyline.getMoveToPoint().getX(), polyline.getMoveToPoint().getY()));
                }
                // line to
                else {
                    polyline.getLineToPointList().add(new Point(eventAttribute.getCurEventPoint().getX(), eventAttribute.getCurEventPoint().getY()));
                }
            }
            // 右键
            else if (MouseButton.RIGHT_BTN == eventAttribute.getMouseButton()) {
                eventAttribute.getDrawPanel().pushCurDrawGraphics2list();
            }

            return true;
        }

        @Override
        public boolean mouseMoved(EventAttribute eventAttribute) {
            List<Point> lineToPoints = polyline.getLineToPointList();
            if (lineToPoints != null) {
                int size = lineToPoints.size();
                if (size != 0) {
                    Point currentLineToPoint = lineToPoints.get(size - 1);
                    currentLineToPoint.setX(eventAttribute.getCurEventPoint().getX());
                    currentLineToPoint.setY(eventAttribute.getCurEventPoint().getY());
                    return true;
                }
            }
            return false;
        }
    };

    private EventListener modifyEventListener = new EventAdapter() {
        @Override
        public boolean mousePressed(EventAttribute eventAttribute) {
            if (intersects(eventAttribute) == -1) {
                return false;
            }

            eventAttribute.getDrawPanel().setCurSelectedGraphics(polyline);

            //
            updateDPCSByIntersectsType();

            if (isDoubleClick(eventAttribute)) {
                PopUpWindow.graphicsModification(polyline);
            }

            return true;
        }

        @Override
        public boolean mouseDragged(EventAttribute eventAttribute) {
            Graphics curSelectedGraphics = null;
            if (eventAttribute.getIntersectsType() == -1
                    || (curSelectedGraphics = eventAttribute.getDrawPanel().getCurSelectedGraphics()) == null
                    || !polyline.getId().equals(curSelectedGraphics.getId())) {
                return false;
            }

            edit(eventAttribute);
            return true;
        }

        @Override
        public boolean mouseReleased(EventAttribute eventAttribute) {
            Graphics curSelectedGraphics = null;
            if ((curSelectedGraphics = eventAttribute.getDrawPanel().getCurSelectedGraphics()) == null
                    || !polyline.getId().equals(curSelectedGraphics.getId())) {
                return false;
            }

            return true;
        }

    };

    @Override
    protected boolean load() {
        // reset
        polyline2D.reset();

        verifyIntersectShapeList.clear();

        //
        Point moveToPoint = null;
        List<Point> lineToPointList = null;
        if ((moveToPoint = polyline.getMoveToPoint()) == null
                || (lineToPointList = polyline.getLineToPointList()) == null
                || lineToPointList.isEmpty()) {
            return false;
        }

        polyline2D.moveTo(moveToPoint.getX(), moveToPoint.getY());
        int intersectsType = 1;
        verifyIntersectShapeList.add(new VerifyIntersectShape(intersectsType++, getMovePoint(moveToPoint.getX(), moveToPoint.getY())));

        int size = lineToPointList.size();
        Point lineToPoint = null;
        for (int i = 0; i < size; i++) {
            lineToPoint = lineToPointList.get(i);
            polyline2D.lineTo(lineToPoint.getX(), lineToPoint.getY());
            verifyIntersectShapeList.add(new VerifyIntersectShape(intersectsType++, getMovePoint(lineToPoint.getX(), lineToPoint.getY())));
        }

        verifyIntersectShapeList.add(new VerifyIntersectShape(0, polyline2D));

        return true;
    }

    private Ellipse2D.Double getMovePoint(double x, double y) {
        Ellipse2D.Double movePoint = new Ellipse2D.Double();
        movePoint.setFrameFromCenter(x, y, x + pointRadius, y + pointRadius);
        return movePoint;
    }

    @Override
    protected void setGraphics0(Graphics graphics) {
        this.polyline = (Polyline) graphics;
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
        graphics2DPainter.getGraphics2D().draw(polyline2D);
    }

    @Override
    protected void drawEditPosition(Graphics2DPainter graphics2DPainter) {
        if (verifyIntersectShapeList == null || verifyIntersectShapeList.isEmpty()) {
            return;
        }

        Graphics2D g2d = graphics2DPainter.getGraphics2D();
        VerifyIntersectShape verifyIntersectShape = null;
        for (int i = 0, size = verifyIntersectShapeList.size(); i < size; i++) {
            verifyIntersectShape = verifyIntersectShapeList.get(i);
            if (verifyIntersectShape == null || verifyIntersectShape.getIntersectsType() == 0) {
                continue;
            }
            g2d.fill(verifyIntersectShape.getShape());
        }
    }

    @Override
    protected void drawArrow(Graphics2DPainter graphics2DPainter) {
        int arrowType = polyline.getArrowType();
        switch (arrowType) {
            case -1:
                // Nothing to do
                break;
            case 3:
            case 1:
                Point moveToPoint = polyline.getMoveToPoint();
                if (moveToPoint != null && !polyline.getLineToPointList().isEmpty()) {
                    Point lineToPoint0 = polyline.getLineToPointList().get(0);
                    drawArrow(graphics2DPainter, moveToPoint, lineToPoint0, polyline.getArrow1());
                }
                if (arrowType == 1) {
                    break;
                }

            case 2:
                if (polyline.getMoveToPoint() != null && !polyline.getLineToPointList().isEmpty()) {
                    Point point1 = null;
                    Point point2 = null;

                    int size = -1;
                    if ((size = polyline.getLineToPointList().size()) == 1) {
                        point1 = polyline.getLineToPointList().get(0);
                        point2 = polyline.getMoveToPoint();

                    } else {
                        point1 = polyline.getLineToPointList().get(size - 1);
                        point2 = polyline.getLineToPointList().get(size - 2);
                    }
                    drawArrow(graphics2DPainter, point1, point2, polyline.getArrow2());
                }
                if (arrowType == 2) {
                    break;
                }

            default:
                break;
        }
    }

    @Override
    public boolean edit(EventAttribute eventAttribute) {
        if (eventAttribute.getIntersectsType() == -1) {
            return false;
        }

        if (eventAttribute.getIntersectsType() == 0) {
            // 计算增量值
            double[] incrementXY = getIncrement2(
                    eventAttribute.getPreEventPoint().getX(),
                    eventAttribute.getPreEventPoint().getY(),
                    eventAttribute.getCurEventPoint().getX(),
                    eventAttribute.getCurEventPoint().getY());
            double incrementX = incrementXY[0];
            double incrementY = incrementXY[1];

            polyline.getMoveToPoint().setX(polyline.getMoveToPoint().getX() + incrementX);
            polyline.getMoveToPoint().setY(polyline.getMoveToPoint().getY() + incrementY);
            int size = polyline.getLineToPointList().size();
            Point lineToPoint = null;
            for (int i = 0; i < size; i++) {
                lineToPoint = polyline.getLineToPointList().get(i);
                lineToPoint.setX(lineToPoint.getX() + incrementX);
                lineToPoint.setY(lineToPoint.getY() + incrementY);
            }

        } else if (eventAttribute.getIntersectsType() == 1) {
            Point moveToPoint = polyline.getMoveToPoint();
            moveToPoint.setX(eventAttribute.getCurEventPoint().getX());
            moveToPoint.setY(eventAttribute.getCurEventPoint().getY());

        } else {
            Point lineToPoint = polyline.getLineToPointList().get(eventAttribute.getIntersectsType() - 2);
            lineToPoint.setX(eventAttribute.getCurEventPoint().getX());
            lineToPoint.setY(eventAttribute.getCurEventPoint().getY());
        }

        updateDPCSByIntersectsType();
        return true;
    }

}
