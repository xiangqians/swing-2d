package com.xiangqian.s2d.gentities;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 图形
 *
 * @author xiangqian
 * @date 14:57 2019/10/27
 */
@Data
@NoArgsConstructor
public class Graphics implements Serializable {
    private static final long serialVersionUID = 2924374282063776373L;
    private String id;
    private GraphicsType type;
}
