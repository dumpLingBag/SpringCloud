package com.rngay.common.contants;

public class RedisKeys {

    public static String getTokenKey(Object key) {
        return String.format(":token:%s", key);
    }

    public static String getFailCount(Object key) {
        return String.format("failCount:%s", key);
    }

    public static String getUserKey(Object key) {
        return String.format("user:%s", key);
    }

    public static String getLoginCodeKey(Object key) {
        return String.format("loginCode:%s", key);
    }

    public static String getFormCommitKey(Object key) {
        return String.format("formCommit:%s", key);
    }

    public static String getVerify(Object key) {
        return String.format("loginVerify:%s", key);
    }

}
