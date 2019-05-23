package com.rngay.service_socket.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageUtil {

    public static List<Integer> sort(String var1, String var2) {
        List<Integer> list = new ArrayList<>();
        list.add(Integer.valueOf(var1));
        list.add(Integer.valueOf(var2));
        list.sort(Integer::compareTo);
        return list;
    }

    public static String receive(String content) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(new Date());
        return "{\"send\":\"0\",\"receive\":\"0\",\"sendReceive\":\"0\",\"content\":\""+ content +"\"," +
                "\"createTime\":\""+format+"\",\"smsType\":\"0\"}";
    }

}
