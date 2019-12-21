package com.xiangqian.s2d.gmodifier.unclosed;

import com.xiangqian.s2d.expand.component.CheckBoxPlus;
import com.xiangqian.s2d.expand.component.GBC;
import com.xiangqian.s2d.expand.component.StrokePlus;
import com.xiangqian.s2d.expand.container.intermediate.ColorChooserTableRowPanel;
import com.xiangqian.s2d.expand.container.intermediate.DoubleTableRowPanel;
import com.xiangqian.s2d.expand.container.intermediate.FloatTableRowPanel;
import com.xiangqian.s2d.expand.container.top.PopUpWindow;
import com.xiangqian.s2d.expand.listener.ChangedListener;
import com.xiangqian.s2d.gentities.Graphics;
import com.xiangqian.s2d.gentities.attr.RGB;
import com.xiangqian.s2d.gentities.closed.Arrow;
import com.xiangqian.s2d.gentities.unclosed.UnclosedGraphics;
import com.xiangqian.s2d.gmodifier.AbstractGraphicsModifier;
import com.xiangqian.s2d.shared.SharedPool;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

/**
 * @author xiangqian
 * @date 18:49 2019/11/30
 */
@Slf4j
public abstract class UnclosedGraphicsModifier extends AbstractGraphicsModifier {

    protected Container container;

    protected StrokePlus stroke;

    private UnclosedGraphics unclosedGraphics;

    // 非封闭图形strokeWidth
    private FloatTableRowPanel strokeWidthTRP;

    // 非封闭图形strokeRGB
    private ColorChooserTableRowPanel strokeRGBCCTRP;

    // ///////////// 箭头1 ///////////////

    private JLabel arrow1Label;

    // 箭头1 是否显示
    private CheckBoxPlus arrow1VisibleCheckBox;

    // 箭头1 角度
    private DoubleTableRowPanel arrow1angleTRP;

    // 箭头1 高度
    private DoubleTableRowPanel arrow1heightTRP;

    // 箭头1 strokeWidth
    private FloatTableRowPanel arrow1StrokeWidthTRP;

    // 箭头1 strokeRGB
    private ColorChooserTableRowPanel arrow1StrokeRGBCCTRP;

    // 箭头1 是否填充
    private CheckBoxPlus arrow1FillCheckBox;

    // ///////////// 箭头2 ///////////////

    private JLabel arrow2Label;

    // 箭头2 是否显示
    private CheckBoxPlus arrow2VisibleCheckBox;

    // 箭头2 角度
    private DoubleTableRowPanel arrow2angleTRP;

    // 箭头2 高度
    private DoubleTableRowPanel arrow2heightTRP;

    // 箭头2 strokeWidth
    private FloatTableRowPanel arrow2StrokeWidthTRP;

    // 箭头2 strokeRGB
    private ColorChooserTableRowPanel arrow2StrokeRGBCCTRP;

    // 箭头2 是否填充
    private CheckBoxPlus arrow2FillCheckBox;

    protected int gridy = 0;

    public UnclosedGraphicsModifier() {
        this.container = new JPanel();
        this.container.setLayout(new GridBagLayout());
        init();
    }

    protected abstract void init0();

    // 设置子类图形
    protected abstract void setGraphics0(Graphics graphics);

    protected abstract boolean load0();

    protected void init() {
        this.container.removeAll();

        this.gridy = 0;

        //
        init0();

        initSeparator();

        // stroke
        initStrokeTRP();

        initSeparator();

        // arrow 1
        initArrow1TRP();

        initSeparator();

        // arrow 2
        initArrow2TRP();
    }

    private void initStrokeTRP() {
        int gridx = 0;
        gridy++;

        // 线宽
        if (strokeWidthTRP == null) {
            strokeWidthTRP = new FloatTableRowPanel("线宽: ");
            strokeWidthTRP.addChangedListener(new ChangedListener<Float>() {
                @Override
                public void changed(Float f) {
                    unclosedGraphics.setStrokeWidth(f);
                    refreshDrawPanel();
                }
            });
        }
        container.add(strokeWidthTRP, new GBC(gridx, gridy).setFill(GBC.CENTER));

        gridx++;

        // 颜色选择器面板
        if (strokeRGBCCTRP == null) {
            strokeRGBCCTRP = new ColorChooserTableRowPanel("线颜色") {
                @Override
                public Component getParentComponent() {
                    return PopUpWindow.getInstance();
                }
            };
            strokeRGBCCTRP.addChangedListener(new ChangedListener<RGB>() {
                @Override
                public void changed(RGB rgb) {
                    unclosedGraphics.setStrokeRGB(rgb.clone());
                    refreshDrawPanel();
                }
            });
        }
        container.add(strokeRGBCCTRP, new GBC(gridx, gridy).setFill(GBC.CENTER));
    }

    private void initArrow1TRP() {
        int gridx = 0;
        gridy++;

        if (arrow1Label == null) {
            arrow1Label = new JLabel("箭头1");
        }
        container.add(arrow1Label, new GBC(gridx++, gridy).setFill(GBC.CENTER));

        if (arrow1VisibleCheckBox == null) {
            arrow1VisibleCheckBox = new CheckBoxPlus("是否显示");
            arrow1VisibleCheckBox.addChangedListener(new ChangedListener<Boolean>() {
                @Override
                public void changed(Boolean b) {
                    arrow1angleTRP.setVisible(b);
                    arrow1heightTRP.setVisible(b);

                    arrow1StrokeWidthTRP.setVisible(b);
                    arrow1StrokeRGBCCTRP.setVisible(b);
                    arrow1FillCheckBox.setVisible(b);

                    setArrowType(1, b);
                }
            });
        }
        container.add(arrow1VisibleCheckBox, new GBC(gridx++, gridy).setFill(GBC.CENTER));

        gridx = 1;
        gridy++;

        // 箭头1 角度
        if (arrow1angleTRP == null) {
            arrow1angleTRP = new DoubleTableRowPanel("角度: ");
            arrow1angleTRP.addChangedListener(new ChangedListener<Double>() {
                @Override
                public void changed(Double d) {
                    if (unclosedGraphics.getArrow1() == null) {
                        unclosedGraphics.setArrow1(new Arrow());
                    }
                    unclosedGraphics.getArrow1().setAngle(d);
                    refreshDrawPanel();
                }
            });
        }
        container.add(arrow1angleTRP, new GBC(gridx++, gridy).setFill(GBC.CENTER));

        // 高度
        if (arrow1heightTRP == null) {
            arrow1heightTRP = new DoubleTableRowPanel("高度: ");
            arrow1heightTRP.addChangedListener(new ChangedListener<Double>() {
                @Override
                public void changed(Double d) {
                    if (unclosedGraphics.getArrow1() == null) {
                        unclosedGraphics.setArrow1(new Arrow());
                    }
                    unclosedGraphics.getArrow1().setHeight(d);
                    refreshDrawPanel();
                }
            });
        }
        container.add(arrow1heightTRP, new GBC(gridx++, gridy).setFill(GBC.CENTER));

        gridx = 1;
        gridy++;

        // 箭头1线宽
        if (arrow1StrokeWidthTRP == null) {
            arrow1StrokeWidthTRP = new FloatTableRowPanel("线宽: ");
            arrow1StrokeWidthTRP.addChangedListener(new ChangedListener<Float>() {
                @Override
                public void changed(Float f) {
                    if (unclosedGraphics.getArrow1() == null) {
                        unclosedGraphics.setArrow1(new Arrow());
                    }
                    unclosedGraphics.getArrow1().setStrokeWidth(f);
                    refreshDrawPanel();
                }
            });
        }
        container.add(arrow1StrokeWidthTRP, new GBC(gridx++, gridy).setFill(GBC.CENTER));

        // 箭头1颜色选择器面板
        if (arrow1StrokeRGBCCTRP == null) {
            arrow1StrokeRGBCCTRP = new ColorChooserTableRowPanel("线颜色") {
                @Override
                public Component getParentComponent() {
                    return PopUpWindow.getInstance();
                }
            };
            arrow1StrokeRGBCCTRP.addChangedListener(new ChangedListener<RGB>() {
                @Override
                public void changed(RGB rgb) {
                    if (unclosedGraphics.getArrow1() == null) {
                        unclosedGraphics.setArrow1(new Arrow());
                    }
                    unclosedGraphics.getArrow1().setStrokeRGB(rgb.clone());
                    refreshDrawPanel();
                }
            });
        }
        container.add(arrow1StrokeRGBCCTRP, new GBC(gridx++, gridy).setFill(GBC.CENTER));

        // 箭头1 是否填充
        if (arrow1FillCheckBox == null) {
            arrow1FillCheckBox = new CheckBoxPlus("是否填充");
            arrow1FillCheckBox.addChangedListener(new ChangedListener<Boolean>() {
                @Override
                public void changed(Boolean b) {
                    if (unclosedGraphics.getArrow1() == null) {
                        unclosedGraphics.setArrow1(new Arrow());
                    }
                    unclosedGraphics.getArrow1().setStrokeType(b ? 2 : 1);
                    refreshDrawPanel();
                }
            });
        }
        container.add(arrow1FillCheckBox, new GBC(gridx++, gridy).setFill(GBC.CENTER));
    }

    private void initArrow2TRP() {
        int gridx = 0;
        gridy++;

        if (arrow2Label == null) {
            arrow2Label = new JLabel("箭头2");
        }
        container.add(arrow2Label, new GBC(gridx++, gridy).setFill(GBC.CENTER));

        if (arrow2VisibleCheckBox == null) {
            arrow2VisibleCheckBox = new CheckBoxPlus("是否显示");
            arrow2VisibleCheckBox.addChangedListener(new ChangedListener<Boolean>() {
                @Override
                public void changed(Boolean b) {
                    arrow2angleTRP.setVisible(b);
                    arrow2heightTRP.setVisible(b);

                    arrow2StrokeWidthTRP.setVisible(b);
                    arrow2StrokeRGBCCTRP.setVisible(b);
                    arrow2FillCheckBox.setVisible(b);

                    setArrowType(2, b);
                }
            });
        }
        container.add(arrow2VisibleCheckBox, new GBC(gridx++, gridy).setFill(GBC.CENTER));

        gridx = 1;
        gridy++;

        // 箭头2 角度
        if (arrow2angleTRP == null) {
            arrow2angleTRP = new DoubleTableRowPanel("角度: ");
            arrow2angleTRP.addChangedListener(new ChangedListener<Double>() {
                @Override
                public void changed(Double d) {
                    if (unclosedGraphics.getArrow2() == null) {
                        unclosedGraphics.setArrow2(new Arrow());
                    }
                    unclosedGraphics.getArrow2().setAngle(d);
                    refreshDrawPanel();
                }
            });
        }
        container.add(arrow2angleTRP, new GBC(gridx++, gridy).setFill(GBC.CENTER));

        // 箭头2 高度
        if (arrow2heightTRP == null) {
            arrow2heightTRP = new DoubleTableRowPanel("高度: ");
            arrow2heightTRP.addChangedListener(new ChangedListener<Double>() {
                @Override
                public void changed(Double d) {
                    if (unclosedGraphics.getArrow2() == null) {
                        unclosedGraphics.setArrow2(new Arrow());
                    }
                    unclosedGraphics.getArrow2().setHeight(d);
                    refreshDrawPanel();
                }
            });
        }
        container.add(arrow2heightTRP, new GBC(gridx++, gridy).setFill(GBC.CENTER));

        gridx = 1;
        gridy++;

        // 箭头2线宽
        if (arrow2StrokeWidthTRP == null) {
            arrow2StrokeWidthTRP = new FloatTableRowPanel("线宽: ");
            arrow2StrokeWidthTRP.addChangedListener(new ChangedListener<Float>() {
                @Override
                public void changed(Float f) {
                    if (unclosedGraphics.getArrow2() == null) {
                        unclosedGraphics.setArrow2(new Arrow());
                    }
                    unclosedGraphics.getArrow2().setStrokeWidth(f);
                    refreshDrawPanel();
                }
            });
        }
        container.add(arrow2StrokeWidthTRP, new GBC(gridx++, gridy).setFill(GBC.CENTER));

        // 箭头2颜色选择器面板
        if (arrow2StrokeRGBCCTRP == null) {
            arrow2StrokeRGBCCTRP = new ColorChooserTableRowPanel("线颜色") {
                @Override
                public Component getParentComponent() {
                    return PopUpWindow.getInstance();
                }
            };
            arrow2StrokeRGBCCTRP.addChangedListener(new ChangedListener<RGB>() {
                @Override
                public void changed(RGB rgb) {
                    if (unclosedGraphics.getArrow2() == null) {
                        unclosedGraphics.setArrow2(new Arrow());
                    }
                    unclosedGraphics.getArrow2().setStrokeRGB(rgb.clone());
                    refreshDrawPanel();
                }
            });
        }
        container.add(arrow2StrokeRGBCCTRP, new GBC(gridx++, gridy).setFill(GBC.CENTER));

        //
        if (arrow2FillCheckBox == null) {
            arrow2FillCheckBox = new CheckBoxPlus("是否填充");
            arrow2FillCheckBox.addChangedListener(new ChangedListener<Boolean>() {
                @Override
                public void changed(Boolean b) {
                    if (unclosedGraphics.getArrow2() == null) {
                        unclosedGraphics.setArrow2(new Arrow());
                    }
                    unclosedGraphics.getArrow2().setStrokeType(b ? 2 : 1);
                    refreshDrawPanel();
                }
            });
        }
        container.add(arrow2FillCheckBox, new GBC(gridx++, gridy).setFill(GBC.CENTER));

    }

    /**
     * 横线
     */
    protected void initSeparator() {
        JSeparator separator = new JSeparator(JSeparator.CENTER);
        separator.setPreferredSize(new Dimension(separator.getPreferredSize().width, 10));
        container.add(separator, new GBC(0, ++gridy, 0, 1).setFill(GridBagConstraints.BOTH));
    }


    private void setArrowType(int arrowType, boolean isAdd) {
        if (unclosedGraphics.getArrowType() == -1) {
            if (isAdd) {
                unclosedGraphics.setArrowType(arrowType);
            }

        } else {
            if (isAdd) {
                unclosedGraphics.setArrowType(unclosedGraphics.getArrowType() | arrowType);
            } else {
                unclosedGraphics.setArrowType(unclosedGraphics.getArrowType() & (~arrowType));
            }
        }
        refreshDrawPanel();
    }

    protected void refreshDrawPanel() {
        SharedPool.get(SharedPool.DrawPanelShare.class).get().refresh();
    }

    private boolean load() {
        //
        load0();

        // stroke
        strokeWidthTRP.setValue(unclosedGraphics.getStrokeWidth());
        strokeRGBCCTRP.setRGB(unclosedGraphics.getStrokeRGB());

        // arrow 1
        arrow1VisibleCheckBox.setSelectedNew(unclosedGraphics.getArrowType() == -1 ? false : (unclosedGraphics.getArrowType() & 1) == 1);
        if (unclosedGraphics.getArrow1() != null) {
            arrow1angleTRP.setValue(unclosedGraphics.getArrow1().getAngle());
            arrow1heightTRP.setValue(unclosedGraphics.getArrow1().getHeight());

            arrow1StrokeWidthTRP.setValue(unclosedGraphics.getArrow1().getStrokeWidth());
            arrow1StrokeRGBCCTRP.setRGB(unclosedGraphics.getArrow1().getStrokeRGB());
            arrow1FillCheckBox.setSelected(unclosedGraphics.getArrow1().getStrokeType() == 2);

        } else {
            arrow1angleTRP.setValue(0d);
            arrow1heightTRP.setValue(0d);

            arrow1StrokeWidthTRP.setValue(0f);
            arrow1StrokeRGBCCTRP.setRGB(null);
            arrow1FillCheckBox.setSelected(false);
        }

        // arrow 2
        arrow2VisibleCheckBox.setSelectedNew(unclosedGraphics.getArrowType() == -1 ? false : (unclosedGraphics.getArrowType() & 2) == 2);
        if (unclosedGraphics.getArrow2() != null) {
            arrow2angleTRP.setValue(unclosedGraphics.getArrow2().getAngle());
            arrow2heightTRP.setValue(unclosedGraphics.getArrow2().getHeight());

            arrow2StrokeWidthTRP.setValue(unclosedGraphics.getArrow2().getStrokeWidth());
            arrow2StrokeRGBCCTRP.setRGB(unclosedGraphics.getArrow2().getStrokeRGB());
            arrow2FillCheckBox.setSelected(unclosedGraphics.getArrow2().getStrokeType() == 2);

        } else {
            arrow2angleTRP.setValue(0d);
            arrow2heightTRP.setValue(0d);

            arrow2StrokeWidthTRP.setValue(0f);
            arrow2StrokeRGBCCTRP.setRGB(null);
            arrow2FillCheckBox.setSelected(false);
        }

        return true;
    }

    @Override
    public Container getContentPane() {
        if (!load()) {
            return null;
        }
        return this.container;
    }

    @Override
    public void setGraphics(Graphics graphics) {
        this.unclosedGraphics = (UnclosedGraphics) graphics;
        setGraphics0(graphics);
    }

}
