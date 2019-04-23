package com.rngay.service_socket.contants;

public class RedisKeys {

    private static final String KEY_BANNED = "socket:banned:%s";
    private static final String SET_KEY = "socket:set:%s";
    private static final String USER_KEY = "socket:user:%s";

    public static String getBanned(String key) {
        return String.format(KEY_BANNED, key);
    }

    public static String getSetKey(String key) {
        return String.format(SET_KEY, key);
    }

    public static String getUserKey(String key) {
        return String.format(USER_KEY, key);
    }

}
