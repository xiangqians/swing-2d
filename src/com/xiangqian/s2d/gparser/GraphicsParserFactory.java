package com.xiangqian.s2d.gparser;

import com.xiangqian.s2d.gentities.Graphics;
import com.xiangqian.s2d.gentities.unclosed.Line;
import com.xiangqian.s2d.gmodifier.GraphicsModifier;
import com.xiangqian.s2d.gparser.closed.ArrowGraphicsParser;
import com.xiangqian.s2d.gparser.closed.RectGraphicsParser;
import com.xiangqian.s2d.gparser.unclosed.LineGraphicsParser;
import com.xiangqian.s2d.gparser.unclosed.PolylineGraphicsParser;
import com.xiangqian.s2d.shared.SharedPool;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

/**
 * 图形解析器工厂
 *
 * @author xiangqian
 * @date 09:11 2019/11/09
 */
@Slf4j
public class GraphicsParserFactory {

    public static GraphicsParser getParser(Graphics graphics) {
        return getParser(graphics, true);
    }

    public static GraphicsParser getParser(Graphics graphics, boolean isSingleton) {
        if (graphics == null) {
            return null;
        }

        Class<? extends AbstractGraphicsParser> clazz = null;
        switch (graphics.getType()) {

            // unclosed
            case LINE: // 线
                clazz = LineGraphicsParser.class;
                break;

            case POLYLINE: // 折线
                clazz = PolylineGraphicsParser.class;
                break;


            // closed
            case RECT: // 矩形
                clazz = RectGraphicsParser.class;
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
                clazz = ArrowGraphicsParser.class;
                break;

            default:
                return null;
        }

        AbstractGraphicsParser graphicsParser = get(clazz, isSingleton);
        graphicsParser.setGraphics(graphics);
        return graphicsParser;
    }

    /**
     * @param clazz
     * @param isSingleton 是否是单例模式
     * @param <T>
     * @return
     */
    private static <T extends AbstractGraphicsParser> T get(Class<T> clazz, boolean isSingleton) {
        if (!isSingleton) {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                log.error("", e);
            }
            return null;
        }

        AbstractGraphicsParser graphicsParser = null;
        if ((graphicsParser = CacheMap.INSTANCE.get(clazz)) == null) {
            synchronized (CacheMap.class) {
                if ((graphicsParser = CacheMap.INSTANCE.get(clazz)) == null) {
                    try {
                        graphicsParser = clazz.newInstance();
                        CacheMap.INSTANCE.put(clazz, graphicsParser);
                    } catch (Exception e) {
                        log.error("", e);
                    }
                }
            }
        }
        return (T) graphicsParser;
    }

    private static class CacheMap {
        private static final HashMap<Class<? extends AbstractGraphicsParser>, AbstractGraphicsParser> INSTANCE;

        static {
            INSTANCE = new HashMap();
        }
    }

}
