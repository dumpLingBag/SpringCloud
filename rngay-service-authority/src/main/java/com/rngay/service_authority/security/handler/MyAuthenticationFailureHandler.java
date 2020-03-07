package com.rngay.service_authority.security.handler;

import com.rngay.common.cache.RedisUtil;
import com.rngay.common.contants.RedisKeys;
import com.rngay.common.enums.ResultCodeEnum;
import com.rngay.common.util.ip.IPUtil;
import com.rngay.common.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @FileName: MyAuthenticationFailureHandler
 * @Company:
 * @author    ljy
 * @Date      2020年02月12日
 * @version   1.0.0
 * @remark:   用户登录系统失败后 需要做的业务操作
 * @explain   当用户登录系统失败后则会进入到此类并执行相关业务
 *
 */
@Component
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        String account = request.getParameter("account");
        //用户登录时身份认证未通过
        if (e instanceof LockedException) {
            ResultUtil.writeJson(response,2, "账户被锁定，请联系管理员!");
        } else if (e instanceof CredentialsExpiredException) {
            ResultUtil.writeJson(response,2, "密码过期，请联系管理员!");
        } else if (e instanceof AccountExpiredException) {
            ResultUtil.writeJson(response, 2, "账户过期，请联系管理员!");
        } else if (e instanceof DisabledException) {
            ResultUtil.writeJson(response, 2, "账户被禁用，请联系管理员!");
        } else if (e instanceof BadCredentialsException) {
            failCount(request, response, account);
        } else {
            ResultUtil.writeJson(response, 2, "登录失败!");
        }
    }

    /**
     * 出错次数过多，限制登录
     * @author pengcheng
     * @date 2020-02-14 16:01
     */
    private void failCount(HttpServletRequest request, HttpServletResponse response, String account) {
        String key = request.getServerName() + "_" + IPUtil.getIPAddress(request) + "_" + account;
        Object value = redisUtil.get(RedisKeys.getFailCount(key));
        if (value == null) {
            redisUtil.set(RedisKeys.getFailCount(key), 1, 30 * 60);
        } else {
            int i = Integer.parseInt(value.toString());
            int expire = 30 * 60;
            if (i > 5) {
                expire = 2 * 60 * 60;
            }
            redisUtil.set(RedisKeys.getFailCount(key), i + 1, expire);
        }
        ResultUtil.writeJson(response, ResultCodeEnum.LOGIN_FAIL);
    }

}
