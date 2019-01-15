package com.rngay.service_authority.interceptor;

import com.rngay.common.cache.RedisUtil;
import com.rngay.service_authority.service.UASystemService;
import com.rngay.service_authority.util.AuthorityUtil;
import com.rngay.service_authority.util.JwtUtil;
import com.rngay.common.exception.BaseException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

@Component
public class OperatorInterceptor extends HandlerInterceptorAdapter {

    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private UASystemService systemService;


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
                throw new BaseException(401, "请先登录");
            }

            if (jwtUtil.isExpired(token)){
                throw new BaseException(401, "登录失效");
            }

            int userId;
            try {
                userId = Integer.parseInt(jwtUtil.getSubject(token));
            } catch (Exception e){
                e.printStackTrace();
                throw new BaseException(500, e.getLocalizedMessage());
            }

            String userToken = systemService.findToken(userId, new Date());
            if (userToken == null || "".equals(userToken)) {
                throw new BaseException(401, "请重新登录");
            }

            if (!token.equals(userToken)){
                throw new BaseException(401, "账号在其他设备上登录");
            }

            Map<String, Object> currentUser = systemService.getCurrentUser(request);
            if (currentUser == null) {
                throw new BaseException(401, "请重新登录");
            }
            if (currentUser.get("enable").equals(1)){
                if (!isAuthorized(request)) {
                    throw new BaseException(403, "访问受限");
                }
            } else {
                throw new BaseException(401, "账号被禁用");
            }
        }
        return true;
    }

    /**
     * 判断是否有访问权限
     * */
    private boolean isAuthorized(HttpServletRequest request){
        Map<String, Object> currentUser = systemService.getCurrentUser(request);
        if (currentUser != null && !currentUser.isEmpty()) {
            return currentUser.get("account").equals("admin");
        }
        return false;
    }

    /**
     * 判断是否是开放的访问地址
     * */
    private boolean isExcludeUrl(String actionName){
        if (actionName.equals("/favicon.ico"))
            return true;
        if (actionName.contains("/authorityLogin/"))
            return true;
        return actionName.contains("/error");
    }

}
