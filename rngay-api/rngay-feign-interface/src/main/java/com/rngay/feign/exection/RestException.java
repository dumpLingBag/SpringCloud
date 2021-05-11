package com.rngay.feign.exection;

//import com.netflix.client.ClientException;
import feign.RetryableException;

public class RestException {

    public static String getMessage(Throwable throwable) {
        throwable.printStackTrace();
        String message = "用户服务异常，请稍后再试";
        if (throwable.getCause() instanceof RetryableException) {
            message = "用户服务更新中";
        } /*else if (throwable.getCause() instanceof ClientException) {
            message = "用户服务未启动";
        }*/
        return message;
    }

}
