package com.xiangqian.s2d.gparser.event;

/**
 * @author xiangqian
 * @date 09:14 2019/11/09
 */
public abstract class EventAdapter implements EventListener {

    @Override
    public boolean mouseMoved(EventAttribute eventAttribute) {
        return false;
    }

    @Override
    public boolean mouseEntered(EventAttribute eventAttribute) {
        return false;
    }

    @Override
    public boolean mousePressed(EventAttribute eventAttribute) {
        return false;
    }

    @Override
    public boolean mouseDragged(EventAttribute eventAttribute) {
        return false;
    }

    @Override
    public boolean mouseReleased(EventAttribute eventAttribute) {
        return false;
    }

    @Override
    public boolean mouseClicked(EventAttribute eventAttribute) {
        return false;
    }

    @Override
    public boolean mouseExited(EventAttribute eventAttribute) {
        return false;
    }
}
