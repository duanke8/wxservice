package com.example.wxservice.test;

import com.alibaba.fastjson.JSONObject;
import com.example.wxservice.entity.button.AbstractButton;
import com.example.wxservice.entity.button.ClickButton;
import com.example.wxservice.entity.button.TopButton;
import com.example.wxservice.entity.button.ViewButton;
import com.example.wxservice.util.HttpRequest;
import com.example.wxservice.util.WxUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: wxservice
 * @description:
 * @author: 段珂 dwx922021
 * @create: 2021-11-20 19:38
 **/
@Slf4j
public class Test {
    public static void main(String[] args) {
        // 段珂
        List<AbstractButton> levelTwoDKList = new ArrayList<>();
        levelTwoDKList.add(new ViewButton("照片列表","http://60.205.209.65:8870/html/dk/dkList.html"));
        levelTwoDKList.add(new ViewButton("上传图片","http://60.205.209.65:8870/html/commonUpload.html?bs=dk"));

        // 任霞
        List<AbstractButton> levelTwoRXList = new ArrayList<>();
        levelTwoRXList.add(new ViewButton("重要照片","http://60.205.209.65:8870/html/dk/rxList.html"));
        levelTwoRXList.add(new ViewButton("上传图片","http://60.205.209.65:8870/html/commonUpload.html?bs=rx"));

        // 段卓言
        List<AbstractButton> levelTwoDZYList = new ArrayList<>();
        levelTwoDZYList.add(new ViewButton("重要照片","http://60.205.209.65:8870/html/dk/dzyList.html"));
        levelTwoDZYList.add(new ViewButton("上传图片","http://60.205.209.65:8870/html/commonUpload.html?bs=dzy"));

        List<AbstractButton> levelOneList = new ArrayList<>();
        levelOneList.add(new ClickButton("段珂", levelTwoDKList));
        levelOneList.add(new ClickButton("任霞", levelTwoRXList));
        levelOneList.add(new ClickButton("段卓言", levelTwoDZYList));

        TopButton topButton = new TopButton();
        topButton.setButton(levelOneList);

        String access_token = WxUtils.getStringToken();
        String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + access_token;
        String result = HttpRequest.sendPost(url, JSONObject.toJSONString(topButton));
        System.out.println(result);
    }


}
