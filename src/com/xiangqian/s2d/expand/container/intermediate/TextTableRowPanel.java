package com.xiangqian.s2d.expand.container.intermediate;

import com.xiangqian.s2d.expand.listener.ChangedListener;

/**
 * @author xiangqian
 * @date 20:58 2019/11/30
 */
public class TextTableRowPanel extends AbstractTableRowPanel<String> {

    public TextTableRowPanel(String labelName) {
        super(labelName);
    }

    @Override
    public void addChangedListener(ChangedListener<String> changedListener) {
        setChangedListener(changedListener);
    }

}
