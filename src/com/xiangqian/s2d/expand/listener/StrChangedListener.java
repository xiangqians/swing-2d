package com.xiangqian.s2d.expand.listener;

import com.xiangqian.s2d.expand.listener.ChangedListener;
import com.xiangqian.s2d.shared.SharedPool;
import com.xiangqian.s2d.util.NumberUtils;

/**
 * @author xiangqian
 * @date 21:00 2019/11/30
 */
public abstract class StrChangedListener implements ChangedListener<String> {

    /**
     * 前置处理器
     */
    public boolean postProcessBefore() {
        return true;
    }

    public boolean doubleValue(double d) {
        return false;
    }

    public boolean floatValue(float f) {
        return false;
    }

    @Override
    public void changed(String text) {
        if (!postProcessBefore()) {
            return;
        }

        if (changedValue(text)) {
            postProcessAfter();
        }
    }

    private boolean changedValue(String text) {
        boolean flag = false;
        boolean tempFlag = false;

        //
        double d = NumberUtils.convert2double(text);
        if (d != -1) {
            tempFlag = doubleValue(d);
            flag = flag || tempFlag;
        }

        //
        float f = NumberUtils.convert2float(text);
        if (f != -1) {
            tempFlag = floatValue(f);
            flag = flag || tempFlag;
        }

        return flag;
    }

    /**
     * 后置处理器
     */
    public boolean postProcessAfter() {
        SharedPool.get(SharedPool.DrawPanelShare.class).get().refresh();
        return true;
    }
}
