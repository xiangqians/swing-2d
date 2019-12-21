package com.xiangqian.s2d.expand.container.intermediate;

import com.xiangqian.s2d.expand.listener.ChangedListener;
import com.xiangqian.s2d.util.NumberUtils;

/**
 * @author xiangqian
 * @date 16:22 2019/12/14
 */
public class FloatTableRowPanel extends AbstractTableRowPanel<Float> {

    public FloatTableRowPanel(String labelName) {
        super(labelName);
    }

    @Override
    public void addChangedListener(ChangedListener<Float> changedListener) {
        setChangedListener(new ChangedListener<String>() {
            @Override
            public void changed(String s) {
                changedListener.changed(NumberUtils.convert2float(s));
            }
        });
    }
}
