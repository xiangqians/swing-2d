package com.xiangqian.s2d.gparser.event;

import com.xiangqian.s2d.container.drawpanel.DrawPanel;
import com.xiangqian.s2d.gentities.Graphics;
import com.xiangqian.s2d.gentities.unclosed.Point;
import com.xiangqian.s2d.shared.SharedPool;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.event.MouseEvent;

/**
 * 事件属性
 *
 * @author xiangqian
 * @date 09:14 2019/11/09
 */
@Data
@NoArgsConstructor
public class EventAttribute {

    private String id;

    // 绘制面板
    private DrawPanel drawPanel;

    // 鼠标按钮类型
    private MouseButton mouseButton;

    // 上一个事件发生点
    private Point preEventPoint;

    // 当前事件发生点
    private Point curEventPoint;

    // 相交类型
    private int intersectsType;

    /**
     * 时间存储
     *
     * @see System#currentTimeMillis()
     */
    private long storeTimeMillis;


    /**
     * 前置处理器
     */
    public void postProcessBefore(Graphics graphics) {
        this.setId(graphics.getId());
    }

    /**
     * 后置处理器
     */
    public void postProcessAfter() {
        // Nothing to do
    }

}

