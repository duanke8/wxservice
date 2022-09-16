package com.example.wxservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.wxservice.dao.UserMapper;
import com.example.wxservice.entity.MessageVo;
import com.example.wxservice.entity.RequestVo;
import com.example.wxservice.entity.UserDto;
import com.example.wxservice.util.WxUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserMapper userMapper;
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
        String fromUserName = "oqJRP6LFVz7PbPc0q3vzcwagfWQU";
        UserDto userDto = getUserDto(fromUserName);
        return JSONObject.toJSONString(userDto);
    }

    private UserDto getUserDto(String fromUserName) {
        QueryWrapper<UserDto> wrapper = new QueryWrapper<>();
        wrapper.eq("from_user_name", fromUserName);
        return userMapper.selectOne(wrapper);
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
        MessageVo messageVo = getMessageVo(request);
        String content = messageVo.getContent();
        String answer = "";

        if (content.contains("登记") && content.contains(",") && content.length() > 10) {
            String[] split = content.split(",");
            UserDto param = new UserDto();
            param.setFromUserName(messageVo.getFromUserName());
            String name = split[1];
            param.setName(name);
            param.setToUserName(messageVo.getToUserName());
            param.setPhoneNumber(split[2]);
            userMapper.insert(param);
            answer = name + "：您好！登记成功";
            return getAnswerMessage(messageVo, answer);
        }

        UserDto userDto = getUserDto(messageVo.getFromUserName());
        if (userDto == null) {
            answer = "您还没有登记，请手动登记后再使用公众号！\n" +
                    "登记方式是给公众号发消息，格式为：登记，姓名，手机号。" +
                    "例如：登记，段珂，18710361423";
            return getAnswerMessage(messageVo, answer);
        }
        answer = returnMap.get(content);
        if (Strings.isBlank(answer)) {
            answer = WxUtils.getAnswer(content);
        }
        answer = userDto.getName() + ":您好！\n" + answer;
        return getAnswerMessage(messageVo, answer);
    }

    private String getAnswerMessage(MessageVo messageVo, String answer) {
        String result = "<xml>\n" +
                "  <ToUserName><![CDATA[" + messageVo.getFromUserName() + "]]></ToUserName>\n" +
                "  <FromUserName><![CDATA[" + messageVo.getToUserName() + "]]></FromUserName>\n" +
                "  <CreateTime>" + System.currentTimeMillis() / 1000 + "</CreateTime>\n" +
                "  <MsgType><![CDATA[text]]></MsgType>\n" +
                "  <Content><![CDATA[" + answer + "]]></Content>\n" +
                "</xml>";
        result = result.replace(" ", "").replace("\n", "");
        log.info("返回消息内容：textMessage result:{}", result);
        return result;
    }

    private MessageVo getMessageVo(HttpServletRequest request) throws DocumentException, IOException {
        SAXReader saxReader = new SAXReader();
        Document read = saxReader.read(request.getInputStream());
        Element rootElement = read.getRootElement();
        List<Element> elements = rootElement.elements();
        JSONObject json = new JSONObject();
        for (Element element : elements) {
            json.put(element.getName(), element.getStringValue());
        }
        log.info("接收到的消息：textMessage jsontextMessage json:{}", json.toJSONString());
        return json.toJavaObject(MessageVo.class);
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
