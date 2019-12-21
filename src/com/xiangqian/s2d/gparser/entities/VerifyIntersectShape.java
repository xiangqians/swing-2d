package com.xiangqian.s2d.gparser.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

/**
 * 校验交叉图形
 *
 * @author xiangqian
 * @date 17:18 2019/11/30
 */
@Data
@NoArgsConstructor
public class VerifyIntersectShape {

    private int intersectsType; // 相交类型值
    private Shape shape; // 图形形状

    public VerifyIntersectShape(int intersectsType, Shape shape) {
        this.intersectsType = intersectsType;
        this.shape = shape;
    }
}
