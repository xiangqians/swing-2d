package com.xiangqian.s2d.expand.component;

import java.awt.*;

/**
 * GBC.java,source code from java核心技术 卷1
 *
 * @author xiangqian
 * @date 21:37 2019/12/14
 */
public class GBC extends GridBagConstraints {

    /**
     * @param gridx the gridx position，组件的横向坐标
     * @param gridy the gridy position，组件的纵向坐标
     */
    public GBC(int gridx, int gridy) {
        this.gridx = gridx;
        this.gridy = gridy;
    }

    /**
     * @param gridx
     * @param gridy
     * @param gridWidth  组件的横向宽度，也就是指组件占用的列数，这与HTML的colspan类似
     * @param gridHeight 组件的纵向长度，也就是指组件占用的行数，这与HTML的rowspan类似
     */
    public GBC(int gridx, int gridy, int gridWidth, int gridHeight) {
        this.gridx = gridx;
        this.gridy = gridy;
        this.gridwidth = gridWidth;
        this.gridheight = gridHeight;
    }

    /**
     * @param anchor the anchor style
     * @return this object for further modification
     */
    public GBC setAnchor(int anchor) {
        this.anchor = anchor;
        return this;
    }

    /**
     * @param fill the fill direction
     * @return this object for further modification
     */

    public GBC setFill(int fill) {
        this.fill = fill;
        return this;
    }

    /**
     * @param weightx the cell weight in x direction，指行的权重，告诉布局管理器如何分配额外的水平空间
     * @param weighty the cell weight in y direction，指列的权重，告诉布局管理器如何分配额外的垂直空间
     * @return this object for further modification
     */

    public GBC setWeight(double weightx, double weighty) {
        this.weightx = weightx;
        this.weighty = weighty;
        return this;
    }

    /**
     * sets the insets of this cell
     *
     * @param distance distance ths spacing to use in all directions
     * @return this object for further modification
     */

    public GBC setInsets(int distance) {
        this.insets = new Insets(distance, distance, distance, distance);
        return this;
    }

    /**
     * @param top    distance ths spacing to use on top
     * @param bottom distance ths spacing to use on bottom
     * @param left   distance ths spacing to use to the left
     * @param right  distance ths spacing to use to the  right
     * @return this object for further modification
     */

    public GBC setInsets(int top, int left, int bottom, int right) {
        this.insets = new Insets(top, left, bottom, right);
        return this;
    }

    /**
     * @param ipadx 组件间的横向间距，组件的宽度就是这个组件的最小宽度加上ipadx值
     * @param ipady 组件间的纵向间距，组件的高度就是这个组件的最小高度加上ipady值
     * @return this object for further modification
     */
    public GBC setIpad(int ipadx, int ipady) {
        this.ipadx = ipadx;
        this.ipady = ipady;
        return this;
    }
}
