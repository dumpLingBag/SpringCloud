package com.rngay.service_authority.security.handler;

import com.rngay.common.cache.RedisUtil;
import com.rngay.common.config.JwtConfig;
import com.rngay.common.contants.RedisKeys;
import com.rngay.common.enums.ResultCodeEnum;
import com.rngay.common.manager.AsyncManager;
import com.rngay.service_authority.manger.AsyncFactory;
import com.rngay.common.util.JsonUtil;
import com.rngay.common.util.JwtUtil;
import com.rngay.common.util.MessageUtils;
import com.rngay.common.util.ResultUtil;
import com.rngay.feign.authority.MenuDTO;
import com.rngay.feign.user.dto.UaUserDTO;
import com.rngay.service_authority.security.exception.MyAuthenticationException;
import com.rngay.service_authority.security.jwt.JwtUserDetails;
import com.rngay.service_authority.service.SystemService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
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
    private RedisUtil redisUtil;
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
        Claims claims = Jwts.claims();
        claims.put("userInfo", JsonUtil.obj2String(userInfo));
        claims.put("role", authorities);
        claims.put("userId", userInfo.getId());
        claims.put("checked", checked);
        String access_token = jwtUtil.generateToken(claims);
        if (StringUtils.isNotBlank(access_token)) {
            access_token = jwtConfig.getPrefix() + " " + access_token;
            saveToken(access_token, userInfo);
            HashMap<String, Object> map = new HashMap<>();
            map.put("userId", userInfo.getId());
            map.put("username", userInfo.getUsername());
            map.put("nickname", userInfo.getNickname());
            map.put("avatar", userInfo.getAvatar());
            map.put("authorities", authentication.getAuthorities());
            List<MenuDTO> menuList = systemService.loadForMenu(userInfo);
            map.put("menuList", menuList);
            Map<String, Object> result = new HashMap<>();
            result.put("userInfo", map);
            result.put("access_token", access_token);
            AsyncManager.me().execute(AsyncFactory.recordLogin(userInfo.getUsername(), "0", MessageUtils.message("user.login.success")));
            ResultUtil.writeJson(response, 0, MessageUtils.message("user.login.success"), result);
        }
        ResultUtil.writeJson(response, 2, MessageUtils.message("user.login.fail"));
    }

    /**
     * 保存 token 到 redis
     * @author pengcheng
     * @date 2020-02-14 11:09
     */
    private void saveToken(String token, UaUserDTO userDTO) {
        try {
            systemService.insertToken(userDTO, token);
        } catch (Exception e) {
            throw new MyAuthenticationException(ResultCodeEnum.LOGIN_INFO_FAIL);
        }
    }

}
