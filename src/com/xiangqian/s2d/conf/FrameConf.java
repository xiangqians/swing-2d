package com.xiangqian.s2d.conf;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiangqian
 * @date 21:48 2019/11/30
 */
@Data
@NoArgsConstructor
@ConfValue("frame")
public class FrameConf implements Conf {

    @ConfValue("title")
    private String title;

    @ConfValue("width")
    private int width;

    @ConfValue("height")
    private int height;
}
