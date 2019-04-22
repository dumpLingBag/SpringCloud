package com.rngay.service_socket.contants;

public class RedisKeys {

    private static final String KEY_BANNED = "socket:banned:%s";

    public static String getBanned(String key) {
        return String.format(KEY_BANNED, key);
    }

}
