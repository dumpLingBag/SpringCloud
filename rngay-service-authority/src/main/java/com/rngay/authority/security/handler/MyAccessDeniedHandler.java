package com.rngay.authority.security.handler;

import com.rngay.authority.constant.Constant;
import com.rngay.common.util.ResultUtil;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 * @FileName: MyAccessDeniedHandler
 * @Company:
 * @author    ljy
 * @Date      2020年02月12日
 * @version   1.0.0
 * @remark:   自定义权限不足 需要做的业务操作
 * @explain   当用户登录系统后访问资源时因权限不足则会进入到此类并执行相关业务
 *
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        ResultUtil.writeJson(response, Constant.FORBIDDEN, "权限不足，无法访问系统资源");
    }
}
