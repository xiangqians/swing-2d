package com.xiangqian.s2d.container.toolbar;

import com.xiangqian.s2d.gentities.GraphicsFactory;
import com.xiangqian.s2d.gentities.GraphicsType;
import com.xiangqian.s2d.shared.SharedPool;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author xiangqian
 * @date 09:58 2019/11/30
 */
@Slf4j
public class CustomToolBar extends JToolBar {

    public CustomToolBar() {
        initGraphicsToolBarBtn();
    }

    private void initGraphicsToolBarBtn() {
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                if (source instanceof OperateBtn) {
                    OperateBtn operateBtn = (OperateBtn) source;
                    SharedPool.get(SharedPool.DrawPanelShare.class).get().setCurDrawGraphics(GraphicsFactory.getGraphics(operateBtn.graphicsType));
                }
            }
        };

        this.add(new OperateBtn("选择", GraphicsType.NO, actionListener));
        this.add(new OperateBtn("线", GraphicsType.LINE, actionListener));
        this.add(new OperateBtn("折线", GraphicsType.POLYLINE, actionListener));
//        this.add(new OperateBtn("矩形", GraphicsType.RECT, actionListener));
//        this.add(new OperateBtn("", null,actionListener));
    }

    /**
     * 操作按钮
     */
    public static class OperateBtn extends JButton {
        // 图形类型
        private GraphicsType graphicsType;

        public OperateBtn(String text, GraphicsType graphicsType, ActionListener actionListener) {
            super(text);
            this.graphicsType = graphicsType;
            this.addActionListener(actionListener);
        }
    }
}
