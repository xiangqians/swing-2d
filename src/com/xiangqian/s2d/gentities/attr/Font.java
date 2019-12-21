package com.xiangqian.s2d.gentities.attr;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 字体
 *
 * @author xiangqian
 * @date 10:11 2019/11/30
 */
@Data
@NoArgsConstructor
public class Font implements Serializable {
    private String name; // 字体名称，如：宋体
    private int style; // 字体风格
    private int size; // 字体大小
    private RGB color; // 字体颜色
}
