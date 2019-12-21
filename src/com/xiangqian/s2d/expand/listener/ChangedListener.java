package com.xiangqian.s2d.expand.listener;

/**
 * 变化监听
 *
 * @author xiangqian
 * @date 20:59 2019/11/30
 */
public interface ChangedListener<T> {

    /**
     * 当发生变化时触发
     *
     * @param t
     */
    void changed(T t);

}
