package com.xiangqian.s2d.expand.container.top;

import com.xiangqian.s2d.gentities.Graphics;
import com.xiangqian.s2d.gmodifier.GraphicsModifier;
import com.xiangqian.s2d.gmodifier.GraphicsModifierFactory;
import com.xiangqian.s2d.shared.SharedPool;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

/**
 * 顶层容器 -- 弹出窗口
 *
 * @author xiangqian
 * @date 19:06 2019/11/30
 */
@Slf4j
public class PopUpWindow extends JDialog {

    public PopUpWindow(Component parentComponent, Container contentPane, String title) {
        this(parentComponent, contentPane, title, new Dimension(800, 600));
    }

    public PopUpWindow(Component parentComponent, Container contentPane, String title, Dimension d) {
        this.setTitle(title);
        this.setModal(true);
        this.setSize(d);
        this.setContentPane(contentPane);
        this.setLocationRelativeTo(parentComponent);
    }

    public static PopUpWindow getInstance(Component parentComponent, Container contentPane, String title) {
        Singleton.POP_UP_WINDOW.setLocationRelativeTo(parentComponent);
        Singleton.POP_UP_WINDOW.setContentPane(contentPane);
        Singleton.POP_UP_WINDOW.setTitle(title);
        return Singleton.POP_UP_WINDOW;
    }

    public static PopUpWindow getInstance() {
        return Singleton.POP_UP_WINDOW;
    }

    public static class Singleton {
        private static final PopUpWindow POP_UP_WINDOW;

        static {
            POP_UP_WINDOW = new PopUpWindow(null, new JPanel(), null);
        }
    }


    /**
     * 图形修改窗口
     *
     * @param graphics
     */
    public static void graphicsModification(Graphics graphics) {
        GraphicsModifier graphicsModifier = GraphicsModifierFactory.getModifier(graphics);
        Container contentPane = null;
        if (graphicsModifier == null || (contentPane = graphicsModifier.getContentPane()) == null) {
            return;
        }

        PopUpWindow popUpWindow = getInstance(SharedPool.get(SharedPool.MainFrameShare.class).get(), contentPane, "图形修改");

        int height = 0;
        for (Component component : contentPane.getComponents()) {
            height += component.getPreferredSize().height;
        }
        popUpWindow.setSize(new Dimension(popUpWindow.getSize().width, height));

        popUpWindow.setVisible(true);
        // blocking

        log.debug("--------popUp finish---------");
    }
}
