package com.xiangqian.s2d.container.frame;

import com.xiangqian.s2d.conf.ConfFactory;
import com.xiangqian.s2d.conf.FrameConf;
import com.xiangqian.s2d.container.drawpanel.DrawPanel;
import com.xiangqian.s2d.container.menubar.CustomMenuBar;
import com.xiangqian.s2d.container.toolbar.CustomToolBar;
import com.xiangqian.s2d.shared.SharedPool;
import com.xiangqian.s2d.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

/**
 * @author xiangqian
 * @date 20:51 2019/11/05
 */
@Slf4j
public class MainFrame extends JFrame {

    public MainFrame() {
        FrameConf frameConf = ConfFactory.get(FrameConf.class);
        this.setTitle(frameConf.getTitle());
        this.setSize(frameConf.getWidth(), frameConf.getHeight());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // menu bar
        this.setJMenuBar(new CustomMenuBar());

        // content pane
        this.setContentPane(new JPanel());
        this.setLayout(new BorderLayout());
        this.add(new CustomToolBar(), BorderLayout.PAGE_START); // BorderLayout.NORTH
        this.add(new DrawPanel(), BorderLayout.CENTER);

        //
        SharedPool.get(SharedPool.MainFrameShare.class).set(this);
    }

}
