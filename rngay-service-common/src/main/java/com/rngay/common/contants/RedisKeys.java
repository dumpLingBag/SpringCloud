package com.rngay.common.contants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedisKeys {

    @Value(value = "{spring.application.name}")
    public static String active;

    public static String getTokenKey(Object key) {
        return String.format(active + ":token:%s", key);
    }

    public static String getFailCount(Object key) {
        return String.format(active + ":failCount:%s", key);
    }

    public static String getUserKey(Object key) {
        return String.format(active + ":user:%s", key);
    }

}
