package com.xiangqian.s2d.gmodifier.closed;

import com.xiangqian.s2d.expand.container.intermediate.ColorChooserTableRowPanel;
import com.xiangqian.s2d.expand.container.intermediate.FloatTableRowPanel;
import com.xiangqian.s2d.expand.container.top.PopUpWindow;
import com.xiangqian.s2d.expand.listener.ChangedListener;
import com.xiangqian.s2d.expand.listener.StrChangedListener;
import com.xiangqian.s2d.gentities.Graphics;
import com.xiangqian.s2d.gentities.attr.RGB;
import com.xiangqian.s2d.gentities.closed.ClosedGraphics;
import com.xiangqian.s2d.gmodifier.AbstractGraphicsModifier;
import com.xiangqian.s2d.shared.SharedPool;

import javax.swing.*;
import java.awt.*;

/**
 * @author xiangqian
 * @date 14:15 2019/12/14
 */
public abstract class ClosedGraphicsModifier extends AbstractGraphicsModifier {

//    protected Container container;
//
//    private ClosedGraphics closedGraphics;
//
//    // 线宽面板
//    private FloatTableRowPanel strokeWidthTableRowPanel;
//
//    // 线宽颜色选择器面板
//    private ColorChooserTableRowPanel strokeRGBColorChooserTableRowPanel;
//
//    public ClosedGraphicsModifier() {
//        this.container = new JPanel();
//        this.container.setLayout(new FlowLayout());
//        init0();
//    }
//
//    // 设置子类图形
//    protected abstract void setGraphics0(Graphics graphics);
//
//    // 初始化组件
//    protected abstract void init();
//
//    // 加载组件
//    protected abstract boolean load();
//
//    protected boolean load0() {
//        strokeWidthTableRowPanel.setValue(closedGraphics.getStrokeWidth());
//        strokeRGBColorChooserTableRowPanel.setRGB(closedGraphics.getStrokeRGB());
//        strokeRGBColorChooserTableRowPanel.setFill(closedGraphics.getStrokeType() == 2);
//
//        load();
//        return true;
//    }
//
//    private void init0() {
//        // 线宽面板
//        strokeWidthTableRowPanel = new FloatTableRowPanel("线宽: ");
//        strokeWidthTableRowPanel.addChangedListener(new ChangedListener<Float>() {
//            @Override
//            public void changed(Float f) {
//                closedGraphics.setStrokeWidth(f);
//                SharedPool.get(SharedPool.DrawPanelShare.class).get().refresh();
//            }
//        });
//        container.add(strokeWidthTableRowPanel);
//
//        // 颜色选择器面板
//        strokeRGBColorChooserTableRowPanel = new ColorChooserTableRowPanel("颜色", "是否填充") {
//            @Override
//            public Component getParentComponent() {
//                return PopUpWindow.getInstance();
//            }
//        };
//        strokeRGBColorChooserTableRowPanel.addRGBChangedListener(new ChangedListener<RGB>() {
//            @Override
//            public void changed(RGB rgb) {
//                closedGraphics.setStrokeRGB(rgb.clone());
//                SharedPool.get(SharedPool.DrawPanelShare.class).get().refresh();
//            }
//        });
//        strokeRGBColorChooserTableRowPanel.addBooleanChangedListener(new ChangedListener<Boolean>() {
//            @Override
//            public void changed(Boolean b) {
//                closedGraphics.setStrokeType(b ? 2 : 1);
//                SharedPool.get(SharedPool.DrawPanelShare.class).get().refresh();
//            }
//        });
//        container.add(strokeRGBColorChooserTableRowPanel);
//
//        //
//        init();
//    }
//
//    @Override
//    public void setGraphics(Graphics graphics) {
//        this.closedGraphics = (ClosedGraphics) graphics;
//        setGraphics0(graphics);
//    }
//
//    @Override
//    public Container getContentPane() {
//        if (!load0()) {
//            return null;
//        }
//        return this.container;
//    }


}
