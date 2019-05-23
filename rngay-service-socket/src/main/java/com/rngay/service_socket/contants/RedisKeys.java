package com.rngay.service_socket.contants;

import java.util.List;

public class RedisKeys {

    private static final String KEY_BANNED = "socket:banned:%s";
    private static final String USER_KEY = "socket:user:%s";
    private static final String USER_MESSAGE = "%s:socket:message:%s";
    private static final String CACHE_MESSAGE = "%s:cache:message:%s";

    public static final String KEY_SET_USER = "socket:set:user";

    public static String getBanned(String key) {
        return String.format(KEY_BANNED, key);
    }

    public static String getUserKey(String key) {
        return String.format(USER_KEY, key);
    }

    public static String getMessage(List<Integer> sort) {
        return String.format(USER_MESSAGE, String.valueOf(sort.get(0)), String.valueOf(sort.get(1)));
    }

    public static String getScheduledMessage(String key1, String key2) {
        return String.format(USER_MESSAGE, key1, key2);
    }

    public static String getCacheMessage(List<Integer> sort) {
        return String.format(CACHE_MESSAGE, String.valueOf(sort.get(0)), String.valueOf(sort.get(1)));
    }

}
