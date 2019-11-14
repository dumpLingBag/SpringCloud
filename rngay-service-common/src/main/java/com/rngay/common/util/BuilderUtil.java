package com.rngay.common.util;

public class BuilderUtil {

    private static StringBuilder builder = new StringBuilder();

    public static StringBuilder builder(String name, String value) {
        return builder.append(name).append(":").append(value).append("_");
    }

    public static String getBuilder() {
        builder.setCharAt(builder.length() - 1, ' ');
        return builder.toString();
    }

    public static boolean getLength() {
        return builder.length() > 0;
    }

}
