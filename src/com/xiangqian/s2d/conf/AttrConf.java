package com.xiangqian.s2d.conf;

import com.xiangqian.s2d.gentities.attr.RGB;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiangqian
 * @date 21:48 2019/11/30
 */
@Data
@NoArgsConstructor
@ConfValue("attr")
public class AttrConf implements Conf {

    @ConfValue("stroke.width")
    private float strokeWidth; // 线宽

    @ConfValue("rgb.default")
    private RGB defaultRgb;

    @ConfValue("rgb.arrive")
    private RGB arriveRgb;


}
