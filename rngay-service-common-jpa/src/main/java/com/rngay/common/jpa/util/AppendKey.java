package com.rngay.common.jpa.util;

public class AppendKey {

    public static void append(StringBuilder columns, StringBuilder values, String key) {
        if (columns.length() > 0){
            columns.append(",").append(key);
            values.append(",?");
        } else {
            columns.append(key);
            values.append("?");
        }
    }

}
