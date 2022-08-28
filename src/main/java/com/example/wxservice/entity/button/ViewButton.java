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
public class ViewButton extends AbstractButton {
    /**
     *            {
     *                "type":"view",
     *                "name":"搜索",
     *                "url":"http://www.soso.com/"
     *             },
     *
     *
     *             
     */
    private String type = "view";
    private String url;

    public ViewButton(String name,String url) {
        super();
        this.name = name;
        this.url = url;
    }


    public ViewButton(String name, List<AbstractButton> sub_button) {
        super();
        this.sub_button = sub_button;
        this.name = name;
    }

}
