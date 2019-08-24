package com.rngay.service_socket.util;

import com.alibaba.fastjson.JSONObject;
import com.riicy.common.cache.RedisUtil;
import com.riicy.common.controller.HttpClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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

    public static List<Integer> sort(Integer var1, Integer var2) {
        List<Integer> list = new ArrayList<>();
        list.add(var1);
        list.add(var2);
        list.sort(Integer::compareTo);
        return list;
    }

    public static String receive(String content, Integer userId) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = dateFormat.format(new Date());
        return "{\"fromUserId\":\"0\",\"toUserId\":\""+ (userId == null ? 0 : userId) +"\",\"userInfoId\":\"0\",\"content\":\""+ content +"\"," +
                "\"createTime\":\""+format+"\",\"smsType\":\"TEXT\",\"smsStatus\":\"0\",\"smsList\":\"false\"," +
                "\"smsStatus\":\"0:\",\"currentPage\":\"''\",\"pageSize\":\"''\"}";
    }

    public static void accessToken(RedisUtil redisUtil) {
        Long expire = redisUtil.getExpire("baidu:token");
        if (expire > 0) {
            if (expire <= 86400) {
                MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
                String url = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=qvDxjGQSGcEdzjbATtXc6wbf&client_secret=IDFsQGejR9g7opWYQqulG7W9iUGUvmMu";
                JSONObject object = HttpClient.httpsPost(url, params, JSONObject.class);
                if (object != null) {
                    int expiresIn = object.getIntValue("expires_in");
                    redisUtil.set("baidu:token", object.getString("access_token"), expiresIn);
                }
            }
        } else {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            String url = "https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=qvDxjGQSGcEdzjbATtXc6wbf&client_secret=IDFsQGejR9g7opWYQqulG7W9iUGUvmMu";
            JSONObject object = HttpClient.httpsPost(url, params, JSONObject.class);
            if (object != null) {
                int expiresIn = object.getIntValue("expires_in");
                redisUtil.set("baidu:token", object.getString("access_token"), expiresIn);
            }
        }
    }

    public static boolean antiSpam(String token, String content) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String url = "https://aip.baidubce.com/rest/2.0/antispam/v2/spam?access_token=" + token;
        params.add("content", content);
        JSONObject object = HttpClient.httpsPost(url, params, JSONObject.class);
        if (object != null) {
            int intValue = object.getJSONObject("result").getIntValue("spam");
            return intValue <= 0;
        }
        return false;
    }

}
