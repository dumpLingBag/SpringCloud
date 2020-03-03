package com.rngay.service_authority.security.jwt;

import com.rngay.common.util.ResultUtil;
import com.rngay.service_authority.security.exception.MyAuthenticationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @FileName: JwtAuthenticationEntryPoint
 * @Date      2020年02月12日
 * @version   1.0.0
 * @remark:   jwt 认证处理类
 *
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        // 用户登录时身份认证未通过
        if(e instanceof BadCredentialsException) {
            ResultUtil.writeJson(response, 1, "用户登录时身份认证失败");
        } else if (e instanceof InsufficientAuthenticationException){
            e.printStackTrace();
            ResultUtil.writeJson(response, 1, "缺少请求头参数，Authorization传递是token值所以参数是必须的");
        } else if (e instanceof MyAuthenticationException) {
            ResultUtil.writeJson(response, 1, e.getMessage());
        } else {
            ResultUtil.writeJson(response, 1, "用户信息错误");
        }
    }
}
