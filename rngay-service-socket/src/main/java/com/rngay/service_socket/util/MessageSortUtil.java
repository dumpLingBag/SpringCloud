package com.rngay.service_socket.util;

import java.util.ArrayList;
import java.util.List;

public class MessageSortUtil {

    public static List<Integer> sort(String var1, String var2) {
        List<Integer> list = new ArrayList<>();
        list.add(Integer.valueOf(var1));
        list.add(Integer.valueOf(var2));
        list.sort(Integer::compareTo);
        return list;
    }

}
