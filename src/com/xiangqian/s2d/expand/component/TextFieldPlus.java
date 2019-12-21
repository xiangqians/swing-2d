package com.xiangqian.s2d.expand.component;


import com.xiangqian.s2d.expand.listener.ChangedListener;
import com.xiangqian.s2d.util.StringUtils;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * @author xiangqian
 * @date 17:19 2019/12/01
 */
public class TextFieldPlus extends JTextField {

    private ChangedListener<String> changedListener;
    private volatile boolean isChangedListener;

    public TextFieldPlus() {
        this(null);
    }

    public TextFieldPlus(String text) {
        super(text);

        this.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changed();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                changed();
            }

            private void changed() {
                if (isChangedListener && changedListener != null) {
                    changedListener.changed(getText());
                }
            }
        });

        this.isChangedListener = true;
    }

    public void setValue(Object value) {
        this.isChangedListener = false;
        this.setText(StringUtils.trim(value));
        this.isChangedListener = true;
    }

    public void addChangedListener(ChangedListener<String> changedListener) {
        this.changedListener = changedListener;
    }

}
