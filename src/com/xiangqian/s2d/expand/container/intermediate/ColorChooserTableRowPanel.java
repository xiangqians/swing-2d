package com.xiangqian.s2d.expand.container.intermediate;

import com.xiangqian.s2d.expand.listener.ChangedListener;
import com.xiangqian.s2d.gentities.attr.RGB;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author xiangqian
 * @date 13:10 2019/12/01
 */
@Slf4j
public class ColorChooserTableRowPanel extends JPanel {

    private Component parentComponent;

    private JLabel label;
    private JButton btn;
    private Color color;
    private RGB defaultRGB;

    private ChangedListener<RGB> changedListener;
    private String labelName;

    public ColorChooserTableRowPanel(String labelName) {
        this(null, labelName);
    }

    /**
     * @param parentComponent 父组件
     * @param labelName       标签名
     */
    public ColorChooserTableRowPanel(Component parentComponent, String labelName) {
        this.parentComponent = parentComponent;
        this.labelName = labelName;
        init();
    }

    private void init() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        // label
        label = new JLabel(labelName);
        add(label);

        // color chooser
        addColorChooser();
    }

    private void addColorChooser() {

        defaultRGB = new RGB(238, 238, 238);

        btn = new JButton("选择颜色");
        btn.setPreferredSize(new Dimension(100, 30));
        btn.setOpaque(true); // 设置为不透明
        btn.setToolTipText(btn.getText());
        btn.addActionListener(new ActionListener() {

            private RGB rgb;

            @Override
            public void actionPerformed(ActionEvent e) {
                Color color = JColorChooser.showDialog(getParentComponent() == null ? parentComponent : getParentComponent(), "选取颜色", ColorChooserTableRowPanel.this.color);
                if (color == null) {
                    return;
                }
                btn.setBackground(color);
                if (changedListener != null) {
                    if (rgb == null) {
                        rgb = new RGB();
                    }
                    rgb.setR(color.getRed());
                    rgb.setG(color.getGreen());
                    rgb.setB(color.getBlue());
                    changedListener.changed(rgb);
                }
            }
        });
        add(btn);
    }

    protected Component getParentComponent() {
        return null;
    }

    public void setRGB(RGB rgb) {
        if (rgb == null) {
            rgb = defaultRGB;
        }
        setRGB(rgb.getR(), rgb.getG(), rgb.getB());
    }

    public void setRGB(int r, int g, int b) {
        color = new Color(r, g, b);
        btn.setBackground(color);
    }

    public void addChangedListener(ChangedListener<RGB> changedListener) {
        this.changedListener = changedListener;
    }
}
