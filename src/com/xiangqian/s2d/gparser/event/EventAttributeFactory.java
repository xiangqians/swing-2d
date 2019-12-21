package com.xiangqian.s2d.gparser.event;

import com.xiangqian.s2d.gentities.unclosed.Point;
import com.xiangqian.s2d.shared.SharedPool;

import java.awt.event.MouseEvent;

/**
 * 事件属性工厂
 *
 * @author xiangqian
 * @date 14:04 2019/11/30
 */
public class EventAttributeFactory {

    /**
     * 获取事件属性并刷新位置
     *
     * @param e
     * @return
     */
    public static EventAttribute getAndRefresh(MouseEvent e) {
        EventAttribute eventAttribute = SharedPool.get(SharedPool.EventAttributeShare.class).get();

        // 1.
        // 上一次事件的cur位置点
        Point curEventPoint = eventAttribute.getCurEventPoint();

        // 将当前事件的pre位置点更新为上一次事件的cur位置点
        Point preEventPoint = eventAttribute.getPreEventPoint();
        preEventPoint.setX(curEventPoint.getX());
        preEventPoint.setY(curEventPoint.getY());

        // 2.
        // 设置事件鼠标按钮类型
        eventAttribute.setMouseButton(MouseButton.get(e.getButton()));

        // 3.
        // 设置当前事件位置点
        curEventPoint.setX(e.getX());
        curEventPoint.setY(e.getY());

        return eventAttribute;
    }

    public static EventAttribute get() {
        return SharedPool.get(SharedPool.EventAttributeShare.class).get();
    }

}
