package com.rngay.service_socket.contants;

public class RedisKeys {

    private static final String KEY_BANNED = "socket:banned:%s";
    private static final String USER_KEY = "socket:user:%s";
    private static final String USER_MESSAGE = "socket:message:%s";

    public static final String KEY_SET_USER = "socket:set:user";

    public static String getBanned(String key) {
        return String.format(KEY_BANNED, key);
    }

    public static String getUserKey(String key) {
        return String.format(USER_KEY, key);
    }

    public static String getMessage(String key) {
        return String.format(USER_MESSAGE, key);
    }

}
