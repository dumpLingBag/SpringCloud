package com.rngay.service_authority.interceptor;

import com.rngay.common.annotation.PreAuthorize;
import com.rngay.common.cache.RedisUtil;
import com.rngay.common.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class AuthorityInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JwtUtil jwtUtil;

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//        Method method = handlerMethod.getMethod();
//        PreAuthorize annotation = method.getAnnotation(PreAuthorize.class);
//        if (annotation != null) {
//            return false;
//        }
//        return true;
//    }
}
