package com.xiangqian.s2d.expand.container.intermediate;

import com.xiangqian.s2d.expand.listener.ChangedListener;
import com.xiangqian.s2d.util.NumberUtils;

/**
 * @author xiangqian
 * @date 16:21 2019/12/14
 */
public class DoubleTableRowPanel extends AbstractTableRowPanel<Double> {

    public DoubleTableRowPanel(String labelName) {
        super(labelName);
    }

    @Override
    public void addChangedListener(ChangedListener<Double> changedListener) {
        setChangedListener(new ChangedListener<String>() {
            @Override
            public void changed(String s) {
                changedListener.changed(NumberUtils.convert2double(s));
            }
        });
    }

}
