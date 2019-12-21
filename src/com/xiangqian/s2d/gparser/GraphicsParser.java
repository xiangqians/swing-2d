package com.xiangqian.s2d.gparser;

import com.xiangqian.s2d.gparser.entities.Graphics2DPainter;
import com.xiangqian.s2d.gparser.event.Event;
import com.xiangqian.s2d.gparser.event.EventAttribute;

/**
 * 图形解析器
 *
 * @author xiangqian
 * @date 09:11 2019/11/09
 */
public interface GraphicsParser {

    /**
     * 触发事件
     *
     * @param event
     * @param eventAttribute
     * @return 是否触发成功
     */
    boolean triggerEvent(Event event, EventAttribute eventAttribute);

    /**
     * 绘制
     *
     * @param graphics2DPainter
     */
    void draw(Graphics2DPainter graphics2DPainter);

    /**
     * (x, y)坐标是否相交与图形
     *
     * @param x x坐标值
     * @param y y坐标值
     * @return -1，不相交；0，相交于图形体；1，相交于拉伸端点
     */
    int intersects(double x, double y);

    /**
     * 图形编辑
     *
     * @param eventAttribute
     * @return
     */
    boolean edit(EventAttribute eventAttribute);

    /**
     * 图形缩放
     * @param eventAttribute
     * @return
     */
//    boolean zoom(EventAttribute eventAttribute);

}
