package com.rngay.service_authority.util;

import org.springframework.http.*;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class HttpClientUtil {

    private HttpClientUtil() {
    }

    private static HttpClientUtil httpClientUtil;

    public static HttpClientUtil getInstance() {
        if (httpClientUtil == null) {
            httpClientUtil = new HttpClientUtil();
        }
        return httpClientUtil;
    }

    public static <T> T post(String url, MultiValueMap<String, String> params, Class<T> t) {
        RestTemplate template = new RestTemplate();
        //新建Http头，add方法可以添加参数
        HttpHeaders headers = new HttpHeaders();
        //设置请求发送方式
        HttpMethod method = HttpMethod.POST;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //执行HTTP请求，将返回的结构使用String 类格式化（可设置为对应返回值格式的类）
        template.postForEntity(url, requestEntity, t).getBody();
        return template.exchange(url, method, requestEntity, t).getBody();
    }

    public static <T> T httpsPost(String url, MultiValueMap<String, String> params, Class<T> t) {
        RestTemplate template = new RestTemplate(new HttpsClientRequestFactory());
        //新建Http头，add方法可以添加参数
        HttpHeaders headers = new HttpHeaders();
        //设置请求发送方式
        HttpMethod method = HttpMethod.POST;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        //将请求头部和参数合成一个请求
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //执行HTTP请求，将返回的结构使用String 类格式化（可设置为对应返回值格式的类）
        template.postForEntity(url, requestEntity, t);
        return template.exchange(url, method, requestEntity, t).getBody();
    }

    public <T> T get(String url, MultiValueMap<String, String> params, Class<T> t) {
        RestTemplate template = new RestTemplate();
        url = getUrl(url, params);
        return template.getForEntity(url, t, params).getBody();
    }

    public <T> T httpsGet(String url, Map<String, ?> params, Class<T> t) {
        RestTemplate template = new RestTemplate(new HttpsClientRequestFactory());
        url = getUrl(url, params);
        return template.getForEntity(url, t, params).getBody();
    }

    private String getUrl(String url, Map<String, ?> params) {
        StringBuilder urlBuilder = new StringBuilder(url + "?");
        for (String key : params.keySet()) {
            urlBuilder.append(key).append("=").append("{").append(key).append("}").append("&");
        }
        urlBuilder.setCharAt(urlBuilder.length() - 1, ' ');
        return urlBuilder.toString();
    }

}
