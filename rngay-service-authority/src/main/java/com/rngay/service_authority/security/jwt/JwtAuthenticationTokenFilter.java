package com.rngay.service_authority.security.jwt;

import com.rngay.common.cache.RedisUtil;
import com.rngay.common.contants.RedisKeys;
import com.rngay.common.enums.ResultCodeEnum;
import com.rngay.common.util.AuthorityUtil;
import com.rngay.common.util.JwtUtil;
import com.rngay.service_authority.security.IgnoredUrlsProperties;
import com.rngay.service_authority.security.exception.MyAuthenticationException;
import com.rngay.service_authority.security.util.SkipPathRequestMatcher;
import com.rngay.service_authority.service.SystemService;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @FileName: JwtAuthenticationTokenFilter
 * @Company:
 * @author    ljy
 * @Date      2020年02月13日
 * @version   1.0.0
 * @remark:   jwt认证token
 * @explain   每次请求接口时 就会进入这里验证token 是否合法
 *             token 如果用户一直在操作，则token 过期时间会叠加    如果超过设置的过期时间未操作  则token 失效 需要重新登录
 *
 */
@SuppressWarnings("unchecked")
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private SystemService systemService;
    @Autowired
    private IgnoredUrlsProperties ignoredUrlsProperties;

    @Value("${token.processingPath}")
    private String processingPath;

    public JwtAuthenticationTokenFilter() {

    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        List<String> pathsToSkip = ignoredUrlsProperties.getPathsToSkip();
        RequestMatcher requestMatcher = new SkipPathRequestMatcher(pathsToSkip, processingPath);
        // 过滤掉不需要token验证的url
        if (!requestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = AuthorityUtil.getRequestToken(request);
        if (StringUtils.isNotBlank(token)) {
            // token 已过期，重新登录
            if (jwtUtil.isExpired(token)) {
                throw new MyAuthenticationException(ResultCodeEnum.TOKEN_INVALID);
            }
            long userId;
            try {
                userId = Long.parseLong(jwtUtil.getSubject(token));
            } catch (Exception e) {
                throw new MyAuthenticationException(ResultCodeEnum.TOKEN_INVALID);
            }
            Object user_token = redisUtil.getHashVal(RedisKeys.getUserKey(userId), "access_token");
            if (user_token == null || "".equals(user_token)) {
                throw new MyAuthenticationException(ResultCodeEnum.TOKEN_INVALID);
            }
            // token 不一致，账号在其他地方登录
            if (!user_token.equals(token)) {
                throw new MyAuthenticationException(ResultCodeEnum.TOKEN_OTHER_LOGIN);
            }
            // 增加 token 过期时间
            boolean checked = (boolean) redisUtil.getHashVal(RedisKeys.getUserKey(userId), "checked");
            if (!checked) {
                // 每次请求延长 token 过期时间
                redisUtil.expire(RedisKeys.getUserKey(userId), 7200);
            }
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || authentication.getPrincipal().equals("anonymousUser")) {
                Claims claims = jwtUtil.getClaimByToken(jwtUtil.getToken(token));
                List<String> roles = claims.get("role", List.class);
                String userInfo = claims.get("userInfo", String.class);
                List<SimpleGrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
                // 生成authentication身份信息
                UsernamePasswordAuthenticationToken usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(
                        userInfo, null, authorities);
                usernamePasswordAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentication);
            }
        } else {
            throw new MyAuthenticationException(ResultCodeEnum.TOKEN_INVALID);
        }
        filterChain.doFilter(request, response);
    }

}
