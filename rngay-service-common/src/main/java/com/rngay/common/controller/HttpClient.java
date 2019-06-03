package com.rngay.common.controller;

import com.riicy.common.util.HttpsClientRequestFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class HttpClient {

    public static <T> T httpsPost(String url, MultiValueMap<String, String> params, Class<T> t) {
        RestTemplate template = new RestTemplate(new HttpsClientRequestFactory());
        return template.postForEntity(url, params, t).getBody();
    }

    public static <T> T httpPost(String url, MultiValueMap<String, String> params, Class<T> t) {
        RestTemplate template = new RestTemplate();
        return template.postForEntity(url, params, t).getBody();
    }

    public static <T> T httpGet(String url, Map<String, ?> params, Class<T> t) {
        RestTemplate template = new RestTemplate();
        StringBuilder urlBuilder = new StringBuilder(url + "?");
        for (String key : params.keySet()) {
            urlBuilder.append(key).append("=").append("{").append(key).append("}").append("&");
        }
        urlBuilder.setCharAt(urlBuilder.length() - 1, ' ');
        return template.getForEntity(urlBuilder.toString().trim(), t, params).getBody();
    }

    public static <T> T httpsGet(String url, Map<String, ?> params, Class<T> t) {
        RestTemplate template = new RestTemplate(new HttpsClientRequestFactory());
        StringBuilder urlBuilder = new StringBuilder(url + "?");
        for (String key : params.keySet()) {
            urlBuilder.append(key).append("=").append("{").append(key).append("}").append("&");
        }
        urlBuilder.setCharAt(urlBuilder.length() - 1, ' ');
        return template.getForEntity(urlBuilder.toString().trim(), t, params).getBody();
    }

}
