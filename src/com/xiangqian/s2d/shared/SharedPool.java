package com.xiangqian.s2d.shared;


import com.xiangqian.s2d.container.drawpanel.DrawPanel;
import com.xiangqian.s2d.container.frame.MainFrame;
import com.xiangqian.s2d.gentities.unclosed.Point;
import com.xiangqian.s2d.gparser.event.EventAttribute;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * 共享池
 *
 * @author xiangqian
 * @date 09:12 2019/11/09
 */
@Slf4j
public class SharedPool {

    public static <T extends Share> T get(Class<T> clazz) {
        Share share = null;
        if ((share = CacheMap.INSTANCE.get(clazz)) == null) {
            synchronized (CacheMap.class) {
                if ((share = CacheMap.INSTANCE.get(clazz)) == null) {
                    try {
                        share = clazz.newInstance();
                        CacheMap.INSTANCE.put(clazz, share);
                    } catch (Exception e) {
                        log.error("", e);
                    }
                }
            }
        }
        return (T) share;
    }

    private static class CacheMap {
        private static final HashMap<Class<? extends Share>, Share> INSTANCE;

        static {
            INSTANCE = new HashMap();
        }
    }

    private static interface Share<T> {
        T get();

        void set(T t);

        void freed();
    }

    public static class MainFrameShare implements Share<MainFrame> {

        private MainFrame mainFrame;

        @Override
        public MainFrame get() {
            return mainFrame;
        }

        @Override
        public void set(MainFrame mainFrame) {
            this.mainFrame = mainFrame;
        }

        @Override
        public void freed() {
            mainFrame = null;
        }
    }

    /**
     * @author xiangqian
     * @date 09:20 2019/11/06
     */
    public static class DrawPanelShare implements Share<DrawPanel> {

        private DrawPanel drawPanel;

        @Override
        public DrawPanel get() {
            return drawPanel;
        }

        @Override
        public void set(DrawPanel drawPanel) {
            this.drawPanel = drawPanel;
        }

        @Override
        public void freed() {
            drawPanel = null;
        }
    }


    /**
     * @author xiangqian
     * @date 14:25 2019/11/29
     */
    public static class EventAttributeShare implements Share<EventAttribute> {

        private volatile EventAttribute eventAttribute;

        @Override
        public EventAttribute get() {
            if (eventAttribute == null) {
                synchronized (this) {
                    if (eventAttribute == null) {
                        eventAttribute = new EventAttribute();
                        eventAttribute.setPreEventPoint(new Point());
                        eventAttribute.setCurEventPoint(new Point());
                        eventAttribute.setDrawPanel(SharedPool.get(DrawPanelShare.class).get());
                    }
                }
            }
            return eventAttribute;
        }

        @Override
        public void set(EventAttribute eventAttribute) {
            this.eventAttribute = eventAttribute;
        }

        @Override
        public void freed() {
            eventAttribute = null;
        }
    }


}
