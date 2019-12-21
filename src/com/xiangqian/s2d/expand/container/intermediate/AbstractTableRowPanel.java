package com.xiangqian.s2d.expand.container.intermediate;

import com.xiangqian.s2d.expand.component.TextFieldPlus;
import com.xiangqian.s2d.expand.listener.ChangedListener;
import com.xiangqian.s2d.util.StringUtils;

import javax.swing.*;
import java.awt.*;

/**
 * 中间容器 -- 表格中的一行面板
 *
 * @author xiangqian
 * @date 16:30 2019/12/14
 */
public abstract class AbstractTableRowPanel<T> extends JPanel {

    private JLabel label;
    private TextFieldPlus textField;

    public AbstractTableRowPanel(String labelName) {
        this(labelName, 6, null);
    }

    public AbstractTableRowPanel(String labelName, int columns, T value) {
        this.setLayout(new FlowLayout());

        // label
        this.label = new JLabel(labelName);
        this.add(this.label);

        // text field
        this.textField = new TextFieldPlus(StringUtils.trim(value));
        this.textField.setColumns(columns);
        this.add(this.textField);
    }

    public void setValue(T value) {
        this.textField.setValue(value);
    }

    protected void setChangedListener(ChangedListener<String> changedListener) {
        this.textField.addChangedListener(changedListener);
    }

    public abstract void addChangedListener(ChangedListener<T> changedListener);

}
