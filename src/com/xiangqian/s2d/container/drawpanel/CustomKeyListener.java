package com.xiangqian.s2d.container.drawpanel;

import com.xiangqian.s2d.gentities.Graphics;
import com.xiangqian.s2d.shared.SharedPool;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 * @author xiangqian
 * @date 16:31 2019/10/27
 */
@Slf4j
public class CustomKeyListener extends KeyAdapter {

    private DrawPanel drawPanel;

    public CustomKeyListener(DrawPanel drawPanel) {
        this.drawPanel = drawPanel;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // delete（127）key
        if (e.getKeyCode() == 127) {
            delCurSelectedGraphics();
        }
    }

    /**
     * 删除当前选中的图形
     */
    private void delCurSelectedGraphics() {
        Graphics curSelectedGraphics = null;

        // 当前绘制面板（DrawPanel）仅处于MODIFY（ 修改）状态和当前选中图形（curSelectedGraphics）不为空时，才进行处理
        if (DrawPanel.State.MODIFY != drawPanel.getState() || (curSelectedGraphics = drawPanel.getCurSelectedGraphics()) == null) {
            return;
        }

        StringBuilder msgBuilder = new StringBuilder();
        msgBuilder.append("确定要删除");
        msgBuilder.append("[ ");
        msgBuilder.append(curSelectedGraphics.getId()).append(", ").append(curSelectedGraphics.getType());
        msgBuilder.append(" ]");
        msgBuilder.append("吗?");
        int option = JOptionPane.showConfirmDialog(SharedPool.get(SharedPool.MainFrameShare.class).get(), msgBuilder, "删除提示", JOptionPane.YES_OPTION, JOptionPane.YES_OPTION, null);

        // delete
        if (option == 0) {
            try {
                boolean flag = drawPanel.removeGraphicsById(curSelectedGraphics.getId());
                if (flag) {
                    drawPanel.refresh();
                }
            } catch (Exception e) {
                log.error("删除基本图形异常", e);
            }
        }
    }
}
