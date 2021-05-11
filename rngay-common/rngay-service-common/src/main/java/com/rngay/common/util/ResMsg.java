package com.rngay.common.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResMsg {

    private static Map<String, String> map = new LinkedHashMap<>();

    public static void builder(String name, String value) {
        map.put(name, value);
    }

    public static String getBuilder() {
        return GsonUtil.GsonString(map);
    }

    public static boolean getLength() {
        return map.size() > 0;
    }

}
