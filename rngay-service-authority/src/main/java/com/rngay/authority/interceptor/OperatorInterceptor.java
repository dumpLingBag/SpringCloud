package com.rngay.authority.interceptor;

import com.rngay.authority.util.AuthorityUtil;
import com.rngay.authority.util.JwtUtil;
import com.rngay.common.exception.BaseException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class OperatorInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private RedisTemplate<String, Object> redisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setContentType("text/html;charset=UTF-8");

        String actionName = request.getRequestURI().replace(request.getContextPath(), "");
        if (isExcludeUrl(actionName)){
            return true;
        } else {
            //获取用户凭证
            String token = AuthorityUtil.getRequestToken(request);

            if (token == null || "".equals(token)){
                throw new BaseException(401,"请先登录");
            }

            if (jwtUtil.isExpired(token)){
                throw new BaseException(401,"登录失效");
            }

            int userId;
            try {
                userId = Integer.parseInt(jwtUtil.getSubject(token));
            } catch (Exception e){
                throw new BaseException(500,e.getLocalizedMessage());
            }
        }
        return true;
    }

    /**
     * 判断是否有访问权限
     * */
    private boolean isAuthorized(HttpServletRequest request){
        return false;
    }

    /**
     * 判断是否是开放的访问地址
     * */
    private boolean isExcludeUrl(String actionName){
        if (actionName.equals("/favicon.ico")){
            return true;
        }
        if (actionName.equals("/authorityLogin/")){
            return true;
        }
        return actionName.contains("/error");
    }

}
