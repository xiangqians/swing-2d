package com.xiangqian.s2d.gmodifier.unclosed;

import com.xiangqian.s2d.expand.component.GBC;
import com.xiangqian.s2d.expand.container.intermediate.DoubleTableRowPanel;
import com.xiangqian.s2d.expand.listener.ChangedListener;
import com.xiangqian.s2d.gentities.Graphics;
import com.xiangqian.s2d.gentities.unclosed.Point;
import com.xiangqian.s2d.gentities.unclosed.Polyline;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiangqian
 * @date 15:51 2019/12/15
 */
@Slf4j
public class PolylineGraphicsModifier extends UnclosedGraphicsModifier {

    private Polyline polyline;

    private int effectiveCompSize; // 有效组件大小
    private List<DoubleTableRowPanel> xTRPlList;
    private List<DoubleTableRowPanel> yTRPlList;

    public PolylineGraphicsModifier() {
        xTRPlList = new ArrayList<>();
        yTRPlList = new ArrayList<>();
    }

    @Override
    protected void init0() {
        if (polyline == null || polyline.getMoveToPoint() == null || polyline.getLineToPointList() == null) {
            return;
        }

        setAllVisible(false);

        DoubleTableRowPanel xTRP = null;
        DoubleTableRowPanel yTRP = null;

        effectiveCompSize = polyline.getLineToPointList().size() + 1;

        int incrementSize = effectiveCompSize - xTRPlList.size();
        if (incrementSize > 0) {
            int beginIndex = xTRPlList.size();
            for (int i = 0; i < incrementSize; i++) {
                final int index = beginIndex + i;

                // x
                xTRP = new DoubleTableRowPanel("x" + (index + 1) + ": ");
                xTRP.addChangedListener(new ChangedListener<Double>() {
                    @Override
                    public void changed(Double d) {
                        Point point = getPoint(index);
                        if (point == null) {
                            return;
                        }
                        point.setX(d);
                        refreshDrawPanel();
                    }
                });
                xTRP.setVisible(false);
                xTRPlList.add(xTRP);

                // y
                yTRP = new DoubleTableRowPanel("y" + (index + 1) + ": ");
                yTRP.addChangedListener(new ChangedListener<Double>() {
                    @Override
                    public void changed(Double d) {
                        Point point = getPoint(index);
                        if (point == null) {
                            return;
                        }
                        point.setY(d);
                        refreshDrawPanel();
                    }
                });
                yTRP.setVisible(false);
                yTRPlList.add(yTRP);
            }
        }

        int gridx = 0;
        for (int i = 0; i < effectiveCompSize; i++) {
            if (i % 2 == 0) {
                gridx = 0;
                gridy++;
            }

            // x
            xTRP = xTRPlList.get(i);
            container.add(xTRP, new GBC(gridx++, gridy).setFill(GBC.CENTER));

            // y
            yTRP = yTRPlList.get(i);
            container.add(yTRP, new GBC(gridx++, gridy).setFill(GBC.CENTER));
        }
    }

    private Point getPoint(int index) {
        if (index == 0) {
            return polyline.getMoveToPoint();
        }

        index = index - 1;
        if (index < polyline.getLineToPointList().size()) {
            return polyline.getLineToPointList().get(index);
        }
        return null;
    }


    @Override
    protected void setGraphics0(Graphics graphics) {
        this.polyline = (Polyline) graphics;
        if (polyline.getMoveToPoint() != null
                && polyline.getLineToPointList() != null
                && (effectiveCompSize = polyline.getLineToPointList().size() + 1) != xTRPlList.size()) {
            init();
        }
    }

    @Override
    protected boolean load0() {
        DoubleTableRowPanel xTRP = null;
        DoubleTableRowPanel yTRP = null;
        Point point = null;
        for (int i = 0, size = effectiveCompSize; i < size; i++) {
            point = getPoint(i);
            if (point == null) {
                break;
            }

            xTRP = xTRPlList.get(i);
            xTRP.setValue(point.getX());
            xTRP.setVisible(true);

            yTRP = yTRPlList.get(i);
            yTRP.setValue(point.getY());
            yTRP.setVisible(true);
        }
        return true;
    }

    private void setAllVisible(boolean isVisible) {
        for (int i = 0, size = xTRPlList.size(); i < size; i++) {
            xTRPlList.get(i).setVisible(isVisible);
            yTRPlList.get(i).setVisible(isVisible);
        }
    }


}
