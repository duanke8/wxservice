package com.example.wxservice.util;

import java.util.UUID;

public class OrderUtil {
    public static String getUUID() {
      return  UUID.randomUUID().toString().replace("-","");
    }
}
