package com.xiangqian.s2d.expand.component;

import com.xiangqian.s2d.expand.listener.ChangedListener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author xiangqian
 * @date 23:44 2019/12/14
 */
public class CheckBoxPlus extends JCheckBox {

    private ChangedListener<Boolean> changedListener;

    public CheckBoxPlus(String text) {
        super(text);

        this.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (changedListener != null) {
                    changedListener.changed(isSelected());
                }
            }
        });
    }

    public void setSelectedNew(boolean isSelected) {
        setSelected(isSelected);
        changedListener.changed(isSelected);
    }

    public void addChangedListener(ChangedListener<Boolean> changedListener) {
        this.changedListener = changedListener;
    }
}
