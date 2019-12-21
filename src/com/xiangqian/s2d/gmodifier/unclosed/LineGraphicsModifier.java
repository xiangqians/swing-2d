package com.xiangqian.s2d.gmodifier.unclosed;

import com.xiangqian.s2d.expand.component.GBC;
import com.xiangqian.s2d.expand.container.intermediate.DoubleTableRowPanel;
import com.xiangqian.s2d.expand.listener.ChangedListener;
import com.xiangqian.s2d.gentities.Graphics;
import com.xiangqian.s2d.gentities.unclosed.Line;
import com.xiangqian.s2d.gentities.unclosed.Point;
import lombok.extern.slf4j.Slf4j;

/**
 * @author xiangqian
 * @date 18:50 2019/11/30
 */
@Slf4j
public class LineGraphicsModifier extends UnclosedGraphicsModifier {

    private volatile Line line;

    private DoubleTableRowPanel x1TableRowPanel;
    private DoubleTableRowPanel y1TableRowPanel;
    private DoubleTableRowPanel x2TableRowPanel;
    private DoubleTableRowPanel y2TableRowPanel;

    public LineGraphicsModifier() {
    }

    @Override
    protected void setGraphics0(Graphics graphics) {
        this.line = (Line) graphics;
    }

    @Override
    protected void init0() {
        int gridx = 0;

        // x1
        x1TableRowPanel = new DoubleTableRowPanel("x1: ");
        x1TableRowPanel.addChangedListener(new ChangedListener<Double>() {
            @Override
            public void changed(Double d) {
                line.getPoint1().setX(d);
                refreshDrawPanel();
            }
        });
        container.add(x1TableRowPanel, new GBC(gridx++, gridy).setFill(GBC.CENTER));

        // y1
        y1TableRowPanel = new DoubleTableRowPanel("y1: ");
        y1TableRowPanel.addChangedListener(new ChangedListener<Double>() {
            @Override
            public void changed(Double d) {
                line.getPoint1().setY(d);
                refreshDrawPanel();
            }
        });
        container.add(y1TableRowPanel, new GBC(gridx++, gridy).setFill(GBC.CENTER));

        // x2
        x2TableRowPanel = new DoubleTableRowPanel("x2: ");
        x2TableRowPanel.addChangedListener(new ChangedListener<Double>() {
            @Override
            public void changed(Double d) {
                line.getPoint2().setX(d);
                refreshDrawPanel();
            }
        });
        container.add(x2TableRowPanel, new GBC(gridx++, gridy).setFill(GBC.CENTER));

        // y2
        y2TableRowPanel = new DoubleTableRowPanel("y2: ");
        y2TableRowPanel.addChangedListener(new ChangedListener<Double>() {
            @Override
            public void changed(Double d) {
                line.getPoint2().setY(d);
                refreshDrawPanel();
            }
        });
        container.add(y2TableRowPanel, new GBC(gridx++, gridy).setFill(GBC.CENTER));
    }

    @Override
    protected boolean load0() {
        //
        Point point1 = line.getPoint1();
        Point point2 = line.getPoint2();
        x1TableRowPanel.setValue(point1.getX());
        y1TableRowPanel.setValue(point1.getY());
        x2TableRowPanel.setValue(point2.getX());
        y2TableRowPanel.setValue(point2.getY());
        return true;
    }

}
