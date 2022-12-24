package com.example.wxservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.wxservice.entity.MessageVo;
import com.example.wxservice.entity.RequestVo;
import com.example.wxservice.util.WxUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@RestController
@Slf4j
public class WeiXinController {
    private Map<String, String> returnMap = new HashMap<String, String>() {
        {
            put("传记", "http://60.205.209.65/zj/");
            put("爷爷的传记", "http://60.205.209.65/zj/");
            put("任霞", "任霞是个大美女");
            put("微信", "http://60.205.209.65/wx/");
        }
    };

    @GetMapping("/testwx")
    public String testWx() {
        return "testwx";
    }

    /**
     * signature = data.signature
     * timestamp = data.timestamp
     * nonce = data.nonce
     * echostr = data.echostr
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/")
    public String testWx1(HttpServletRequest request, HttpServletResponse response) {
        RequestVo requestVo = new RequestVo();
        requestVo.setSignature(request.getParameter("signature"));
        requestVo.setTimestamp(request.getParameter("timestamp"));
        requestVo.setNonce(request.getParameter("nonce"));
        requestVo.setEchostr(request.getParameter("echostr"));
        log.info("testWx1 requestVo:{}", JSONObject.toJSONString(requestVo));

        if (checkRequest(requestVo)) {
            return requestVo.getEchostr();
        }
        return "testwx1";
    }

    @PostMapping("/")
    public String textMessage(HttpServletRequest request, HttpServletResponse response) throws IOException, DocumentException {
        SAXReader saxReader = new SAXReader();
        Document read = saxReader.read(request.getInputStream());
        Element rootElement = read.getRootElement();
        List<Element> elements = rootElement.elements();
        JSONObject json = new JSONObject();
        for (Element element : elements) {
            json.put(element.getName(), element.getStringValue());
        }
        log.info("textMessage json:{}", json.toJSONString());
        MessageVo messageVo = json.toJavaObject(MessageVo.class);
        String content = messageVo.getContent();
        String answer = returnMap.get(content);
        if (Strings.isBlank(answer)) {
            answer = WxUtils.getAnswer(content);
        }
        String name = userMap.get(messageVo.getFromUserName());
        answer = name + answer;
        String result = "<xml>\n" +
                "  <ToUserName><![CDATA[" + messageVo.getFromUserName() + "]]></ToUserName>\n" +
                "  <FromUserName><![CDATA[" + messageVo.getToUserName() + "]]></FromUserName>\n" +
                "  <CreateTime>" + System.currentTimeMillis() / 1000 + "</CreateTime>\n" +
                "  <MsgType><![CDATA[text]]></MsgType>\n" +
                "  <Content><![CDATA[" + answer + "]]></Content>\n" +
                "</xml>";

        result = "<xml>\n" +
                "  <ToUserName><![CDATA[" + messageVo.getFromUserName() + "]]></ToUserName>\n" +
                "  <FromUserName><![CDATA[" + messageVo.getToUserName() + "]]></FromUserName>\n" +
                "  <CreateTime>" + System.currentTimeMillis() / 1000 + "</CreateTime>\n" +
                "  <MsgType><![CDATA[news]]></MsgType>\n" +
                "  <ArticleCount>1</ArticleCount>\n" +
                "  <Articles>\n" +
                "    <item>\n" +
                "      <Title><![CDATA[感谢支持，订单已生成，后续查询物流请点击此链接查看]]></Title>\n" +
                "      <Description><![CDATA[订单详情："+content+"]]></Description>\n" +
                "      <PicUrl><![CDATA[http://60.205.209.65/222.png]]></PicUrl>\n" +
                "      <Url><![CDATA[https://www.baidu.com/]]></Url>\n" +
                "    </item>\n" +
                "  </Articles>\n" +
                "</xml>";
        result = result.replace(" ", "").replace("\n", "");
        log.info("textMessage result:{}", result);


        return result;
    }

    /**
     * 创建菜单
     */
    @PostMapping("/createMenu")
    public void createMenu() {

    }


    private boolean checkRequest(RequestVo requestVo) {
        ArrayList<String> list = new ArrayList<>();
        list.add(requestVo.getTimestamp());
        list.add(requestVo.getNonce());
        list.add(WxUtils.TOKEN);
        Collections.sort(list);
        log.info("排序后的结果 : {} ", list);

        String result = WxUtils.sha1Encrypt(String.join("", list));
        log.info("signature: {}", requestVo.getSignature());
        log.info("result： {}", result);

        if (requestVo.getSignature().equalsIgnoreCase(result)) {
            log.info("接入成功");
            return true;
        } else {
            log.error("接入失败");
            return false;
        }
    }

    private Map<String, String> userMap = new HashMap<String, String>() {
        {
            put("oYsQ75ucVxh5XV8BPdhUjmA-wdi8", "段珂，您好！\n");
            put("oYsQ75iUyUO4CspgIKSWf3i3-ElE", "任霞，您好！\n");
            put("oYsQ75qfkZNtb4R1dfvZN1I8nz6Y", "赵鹏程，您好！\n");
        }
    };
}
