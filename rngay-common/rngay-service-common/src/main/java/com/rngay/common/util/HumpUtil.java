package com.rngay.common.util;

public class HumpUtil {

    /**
    * 下划线转驼峰
    * @Author pengcheng
    * @Date 2018/12/6
    **/
    public static String lineToHump(String para){
        StringBuilder builder = new StringBuilder();
        String[] lines = para.split("_");
        for (String line : lines) {
            if (builder.length() == 0) {
                builder.append(line.toLowerCase());
            } else {
                builder.append(line.substring(0, 1).toUpperCase());
                builder.append(line.substring(1).toLowerCase());
            }
        }
        return builder.toString();
    }

    /**
    * 驼峰转下划线
    * @Author pengcheng
    * @Date 2018/12/6
    **/
    public static String humpToLine(String para){
        StringBuilder builder = new StringBuilder(para);
        int temp = 0;
        for (int i = 0; i < para.length(); i++){
            if (Character.isUpperCase(para.charAt(i))) {
                builder.insert(i+temp, "_");
                temp += 1;
            }
        }
        return builder.toString().toLowerCase();
    }

}
