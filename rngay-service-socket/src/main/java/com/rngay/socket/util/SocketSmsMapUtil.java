package com.rngay.socket.util;

import com.rngay.socket.NettyWebSocket;

import java.util.concurrent.ConcurrentHashMap;

public class SocketSmsMapUtil {

    private static ConcurrentHashMap<String, NettyWebSocket> webSocketMap = new ConcurrentHashMap<>();

    public static void put(String key, NettyWebSocket socket) {
        webSocketMap.put(key, socket);
    }

    public static NettyWebSocket get(String key) {
        return webSocketMap.get(key);
    }

    public static void remove(String key) {
        webSocketMap.remove(key);
    }

}
