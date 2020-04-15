package com.rngay.authority.security.handler;

import com.rngay.authority.constant.Constant;
import com.rngay.common.util.ResultUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @FileName: MyAuthenticationEntryPointHandler
 * @Company:
 * @author    ljy
 * @Date      2020年02月12日
 * @version   1.0.0
 * @remark:   认证失败 需要做的业务操作
 * @explain   当检测到用户访问系统资源认证失败时则会进入到此类并执行相关业务
 *
 */
@Component
public class MyAuthenticationEntryPointHandler implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        ResultUtil.writeJson(response, Constant.REQUEST_TIMEOUT, "因为登录超时，无法访问系统资源");
    }
}
