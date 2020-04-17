package com.rngay.authority.security.handler;

import com.rngay.common.contants.ResultCode;
import com.rngay.common.util.MessageUtils;
import com.rngay.common.util.ResultUtil;
import com.rngay.common.vo.Result;
import com.rngay.feign.user.dto.UaUserDTO;
import com.rngay.authority.security.jwt.JwtUserDetails;
import com.rngay.authority.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @FileName: MyLogoutSuccessHandler
 * @Company:
 * @author    ljy
 * @Date      2020年02月12日
 * @version   1.0.0
 * @remark:   用户退出系统成功后 需要做的业务操作
 * @explain   当用户退出系统成功后则会进入到此类并执行相关业务
 *
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private SystemService systemService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        JwtUserDetails details = (JwtUserDetails) authentication.getPrincipal();
        UaUserDTO userInfo = details.getUserInfo();
        if (userInfo != null) {
            systemService.deleteToken(userInfo.getId());
        }
        ResultUtil.writeJson(response, ResultCode.SUCCESS, MessageUtils.message("user.logout.success"));
    }
}
