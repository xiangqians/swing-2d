package com.xiangqian.s2d.gparser.closed;

import com.xiangqian.s2d.gentities.Graphics;
import com.xiangqian.s2d.gparser.AbstractGraphicsParser;
import com.xiangqian.s2d.gparser.entities.Graphics2DPainter;
import com.xiangqian.s2d.gparser.entities.VerifyIntersectShape;
import com.xiangqian.s2d.gparser.event.EventAttribute;
import com.xiangqian.s2d.gparser.event.EventListener;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author xiangqian
 * @date 12:50 2019/12/14
 */
@Slf4j
public class ClosedGraphicsParser extends AbstractGraphicsParser {
    @Override
    public void setGraphics(Graphics graphics) {

    }

    @Override
    protected EventListener defaultEventListener() {
        return null;
    }

    @Override
    protected EventListener drawingEventListener() {
        return null;
    }

    @Override
    protected EventListener modifyEventListener() {
        return null;
    }

    @Override
    protected List<VerifyIntersectShape> getVerifyIntersectShapeList() {
        return null;
    }

    @Override
    public void draw(Graphics2DPainter graphics2DPainter) {

    }

    //    @Override
    public boolean edit(EventAttribute eventAttribute) {
        return false;
    }
}
