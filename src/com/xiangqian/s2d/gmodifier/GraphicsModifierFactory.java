package com.xiangqian.s2d.gmodifier;

import com.xiangqian.s2d.gentities.Graphics;
import com.xiangqian.s2d.gmodifier.unclosed.LineGraphicsModifier;
import com.xiangqian.s2d.gmodifier.unclosed.PolylineGraphicsModifier;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * @author xiangqian
 * @date 18:48 2019/11/30
 */
@Slf4j
public class GraphicsModifierFactory {

    public static GraphicsModifier getModifier(Graphics graphics) {
        return getModifier(graphics, true);
    }

    public static GraphicsModifier getModifier(Graphics graphics, boolean isSingleton) {
        if (graphics == null) {
            return null;
        }

        Class<? extends AbstractGraphicsModifier> clazz = null;
        switch (graphics.getType()) {
            // unclosed
            case LINE: // 线
                clazz = LineGraphicsModifier.class;
                break;

            case POLYLINE: // 折线
                clazz = PolylineGraphicsModifier.class;
                break;


            // closed
            case RECT: // 矩形
                break;

            case IMAGE: // 图片
                break;

            case TEXT: // 文本
                break;

            case ELLIPSE: // 椭圆
                break;

            case POLYGON: // 多边形
                break;

            case ARROW: // 箭头
                break;

            default:
                return null;
        }

        AbstractGraphicsModifier graphicsModifier = get(clazz, isSingleton);
        graphicsModifier.setGraphics(graphics);
        return graphicsModifier;
    }

    /**
     * @param clazz       key
     * @param isSingleton 是否缓存
     * @param <T>
     * @return
     */
    private static <T extends AbstractGraphicsModifier> T get(Class<T> clazz, boolean isSingleton) {
        if (!isSingleton) {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                log.error("", e);
            }
            return null;
        }

        AbstractGraphicsModifier graphicsModifier = null;
        if ((graphicsModifier = GraphicsModifierFactory.CacheMap.INSTANCE.get(clazz)) == null) {
            synchronized (GraphicsModifierFactory.CacheMap.class) {
                if ((graphicsModifier = GraphicsModifierFactory.CacheMap.INSTANCE.get(clazz)) == null) {
                    try {
                        graphicsModifier = clazz.newInstance();
                        GraphicsModifierFactory.CacheMap.INSTANCE.put(clazz, graphicsModifier);
                    } catch (Exception e) {
                        log.error("", e);
                    }
                }
            }
        }
        return (T) graphicsModifier;
    }

    private static class CacheMap {
        private static final HashMap<Class<? extends AbstractGraphicsModifier>, AbstractGraphicsModifier> INSTANCE;

        static {
            INSTANCE = new HashMap();
        }
    }


}
