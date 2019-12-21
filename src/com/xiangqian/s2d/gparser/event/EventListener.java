package com.xiangqian.s2d.gparser.event;

/**
 * 状态监听
 *
 * @author xiangqian
 * @date 09:14 2019/11/09
 */
public interface EventListener {

    // 鼠标移动事件
    boolean mouseMoved(EventAttribute eventAttribute);

    // 鼠标进入事件
    boolean mouseEntered(EventAttribute eventAttribute);

    // 鼠标按下事件
    boolean mousePressed(EventAttribute eventAttribute);

    // 鼠标拖动事件
    boolean mouseDragged(EventAttribute eventAttribute);

    // 鼠标释放事件
    boolean mouseReleased(EventAttribute eventAttribute);

    // 鼠标点击事件
    boolean mouseClicked(EventAttribute eventAttribute);

    // 鼠标退出监听
    boolean mouseExited(EventAttribute eventAttribute);

}
