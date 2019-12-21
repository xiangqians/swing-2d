package com.xiangqian.s2d.container.menubar;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author xiangqian
 * @date 09:55 2019/11/30
 */
@Slf4j
public class CustomMenuBar extends JMenuBar {

    public CustomMenuBar() {
        this.add(new FileMenu());
    }

    public static class FileMenu extends JMenu {

        public FileMenu() {
            super("文件");
            this.add(createOpenFileMenuItem());
            this.add(createSaveFileMenuItem());
        }

        private JMenuItem createOpenFileMenuItem() {
            JMenuItem menuItem = new JMenuItem("打开");
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    log.debug("[打开]-选中");
                }
            });
            return menuItem;
        }

        private JMenuItem createSaveFileMenuItem() {
            JMenuItem menuItem = new JMenuItem("保存");
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    log.debug("[保存]-选中");
                }
            });
            return menuItem;
        }
    }

}
