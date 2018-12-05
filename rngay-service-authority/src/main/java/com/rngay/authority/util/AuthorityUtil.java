package com.rngay.authority.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Component
public class AuthorityUtil {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取请求的token
     */
    public static String getRequestToken(HttpServletRequest request){
        //从header中获取token
        String token = request.getHeader("token");
        //如果header中不存在token，则从参数中获取token
        if(StringUtils.isBlank(token)){
            token = request.getParameter("token");
        }
        return token;
    }

}
