package com.rngay.authority.security.handler;

import com.rngay.authority.constant.Constant;
import com.rngay.common.config.JwtConfig;
import com.rngay.authority.enums.ResultCodeEnum;
import com.rngay.common.manager.AsyncManager;
import com.rngay.authority.manger.AsyncFactory;
import com.rngay.common.util.JwtUtil;
import com.rngay.common.util.MessageUtils;
import com.rngay.common.util.ResultUtil;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.MenuDTO;
import com.rngay.feign.user.dto.UaUserDTO;
import com.rngay.authority.security.exception.MyAuthenticationException;
import com.rngay.authority.security.jwt.JwtUserDetails;
import com.rngay.authority.service.SystemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @FileName: MyAuthenticationSuccessHandler
 * @Company:
 * @author    ljy
 * @Date      2020年02月12日
 * @version   1.0.0
 * @remark:   用户登录系统成功后 需要做的业务操作
 * @explain   当用户登录系统成功后则会进入到此类并执行相关业务
 *
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private SystemService systemService;
    @Autowired
    private JwtConfig jwtConfig;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String checked = request.getParameter("checked");
        // 获得授权后可得到用户信息(jwt 方式)
        JwtUserDetails userDetails = (JwtUserDetails) authentication.getPrincipal();
        UaUserDTO userInfo = userDetails.getUserInfo();
        userInfo.setChecked(Boolean.valueOf(checked));
        List<String> authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        // 使用jwt生成 token 用于权限效验
        String accessToken = jwtUtil.generateToken(userInfo.getId());
        if (StringUtils.isNotBlank(accessToken)) {
            accessToken = jwtConfig.getPrefix() + " " + accessToken;
            saveToken(accessToken, userInfo, authorities);
            HashMap<String, Object> map = new HashMap<>();
            map.put("userId", userInfo.getId());
            map.put("username", userInfo.getUsername());
            map.put("nickname", userInfo.getNickname());
            map.put("avatar", userInfo.getAvatar());
            map.put("authorities", authorities);
            List<MenuDTO> menuList = systemService.listForMenu(userInfo, Constant.POWER);
            map.put("menuList", menuList);
            Map<String, Object> result = new HashMap<>();
            result.put("userInfo", map);
            result.put("access_token", accessToken);
            AsyncManager.me().execute(AsyncFactory.recordLogin(userInfo.getUsername(), userInfo.getOrgId(), Result.CODE_SUCCESS, MessageUtils.message("user.login.success")));
            ResultUtil.writeJson(response, Result.CODE_SUCCESS, MessageUtils.message("user.login.success"), result);
        }
        ResultUtil.writeJson(response, Result.CODE_FAIL_MSG, MessageUtils.message("user.login.fail"));
    }

    /**
     * 保存 token 到 redis
     * @author pengcheng
     * @date 2020-02-14 11:09
     */
    private void saveToken(String token, UaUserDTO userDTO, List<String> authorities) {
        try {
            systemService.insertToken(userDTO, token, authorities);
        } catch (Exception e) {
            throw new MyAuthenticationException(ResultCodeEnum.LOGIN_INFO_FAIL);
        }
    }

}
