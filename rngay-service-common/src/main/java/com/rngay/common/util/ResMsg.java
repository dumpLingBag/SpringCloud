package com.rngay.common.util;

import com.alibaba.fastjson.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResMsg {

    private static Map<String, String> map = new LinkedHashMap<>();

    public static void builder(String name, String value) {
        map.put(name, value);
    }

    public static String getBuilder() {
        return JSONObject.toJSONString(map);
    }

    public static boolean getLength() {
        return map.size() > 0;
    }

}
