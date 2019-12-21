package com.xiangqian.s2d;

import com.xiangqian.s2d.container.frame.MainFrame;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiangqian
 * @date 14:08 2019/11/30
 */
@Slf4j
public class ApplicationMain {

    public static void main(String[] args) {
        try {
            // Visible
            new MainFrame().setVisible(true);
        } catch (Exception e) {
            log.error("", e);
            System.exit(1);
        }
    }
}
