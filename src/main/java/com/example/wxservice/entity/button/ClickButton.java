package com.example.wxservice.entity.button;

import lombok.Data;

import java.util.List;

/**
 * @program: wxservice
 * @description:
 * @author: 段珂 dwx922021
 * @create: 2021-11-20 19:00
 **/
@Data
public class ClickButton extends AbstractButton {
    /**
     * {
     * "type":"click",
     * "name":"今日歌曲",
     * "key":"V1001_TODAY_MUSIC"
     * }
     */
    private String type = "click";
    private String key = "V1001_TODAY_MUSIC";

    public ClickButton(String name) {
        super();
        this.name = name;
    }


    public ClickButton(String name, List<AbstractButton> sub_button) {
        super();
        this.sub_button = sub_button;
        this.name = name;
    }

}
