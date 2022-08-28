package com.example.wxservice.util;

import com.alibaba.fastjson.JSONObject;
import com.example.wxservice.entity.Token;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;

public class WxUtils {
    public static String TOKEN = "duankeloverenxia";
    public static String APPID = "wxc0595b0d4d58bc93";
    public static String APPSECRET = "5170747649f8b8d8a6493fa28dd24009";

    public static String getStringToken() {
        Token token = getToken();
        return token.getAccess_token();
    }

    /**
     * 使用SHA1算法对字符串进行加密
     */
    public static String sha1Encrypt(String str) {
        return sha1Encrypt1(str);
    }

    public static String getAnswer(String message) throws UnsupportedEncodingException {
        return getAnswer1(message);
    }

    public static Token getToken() {
        String tokenurl = "https://api.weixin.qq.com/cgi-bin/token";
        String param = "grant_type=client_credential&appid=" + APPID + "&secret=" + APPSECRET;
        String tokenResult = HttpRequest.sendGet(tokenurl, param);
        System.out.println(tokenResult);
        return JSONObject.parseObject(tokenResult, Token.class);
    }

    /**
     * +    URL 中+号表示空格                                 %2B
     * 空格 URL中的空格可以用+号或者编码           %20
     * /   分隔目录和子目录                                     %2F
     * ?    分隔实际的URL和参数                             %3F
     * %    指定特殊字符                                          %25
     * #    表示书签                                                  %23
     * &    URL 中指定的参数间的分隔符                  %26
     * =    URL 中指定参数的值                                %3D
     */
    private static String getAnswer1(String message) throws UnsupportedEncodingException {
        String url = "http://api.qingyunke.com/api.php";
        message = message.replaceAll(" ", "");
        message = URLEncoder.encode(message, "utf-8");

        String param = "key=free&appid=0&msg=" + message;
        String result = HttpRequest.sendGet(url, param);
        JSONObject jsonObject = JSONObject.parseObject(result);
        return jsonObject.getString("content");
    }


    private static String sha1Encrypt1(String str) {
        if (str == null || str.length() == 0) {
            return null;
        }
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;

            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }

            return new String(buf);

        } catch (Exception e) {
            return null;
        }
    }
}
