package com.xiangqian.s2d.gparser.event;

import java.awt.event.MouseEvent;

/**
 * 鼠标按钮类型
 *
 * @author xiangqian
 * @date 16:12 2019/11/30
 */
public enum MouseButton {

    /**
     * 左键按下
     *
     * @see java.awt.event.MouseEvent#BUTTON1
     */
    LEFT_BTN(MouseEvent.BUTTON1),

    /**
     * 右键按下
     *
     * @see java.awt.event.MouseEvent#BUTTON3
     */
    RIGHT_BTN(MouseEvent.BUTTON3),;

    private final int button;

    MouseButton(int button) {
        this.button = button;
    }

    public static MouseButton get(int button) {
        for (MouseButton mouseButton : values()) {
            if (mouseButton.button == button) {
                return mouseButton;
            }
        }
        return null;
    }

}
