package com.rngay.gateway.filter;

import com.rngay.common.cache.RedisUtil;
import com.rngay.common.contants.RedisKeys;
import com.rngay.common.util.AuthorityUtil;
import com.rngay.common.util.JwtUtil;
import com.rngay.feign.user.dto.UAUserDTO;
import com.rngay.gateway.service.SystemService;
import com.rngay.gateway.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.*;

@Component
public class AuthorizeGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthorizeGatewayFilterFactory.Config> {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SystemService systemService;


    public AuthorizeGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("enabled");
    }

    @Override
    public GatewayFilter apply(AuthorizeGatewayFilterFactory.Config config) {
        return (exchange, chain) -> {
            // 不用验证身份的放行
            if (!config.isEnabled()) {
                return chain.filter(exchange);
            }

            ServerHttpRequest request = exchange.getRequest();
            String actionName = request.getURI().getPath().replace(request.getPath().contextPath().value(), "");
            if (isExcludeUrl(actionName)) {
                return chain.filter(exchange);
            }
            String token = TokenUtil.getRequestToken(request);
            ServerHttpResponse response = exchange.getResponse();
            if (token == null || "".equals(token)) {
                return token(response, HttpStatus.UNAUTHORIZED);
            }
            if (jwtUtil.isExpired(token)) {
                return token(response, HttpStatus.UNAUTHORIZED);
            }
            long userId;
            try {
                userId = Long.parseLong(jwtUtil.getSubject(token));
            } catch (Exception e) {
                return token(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Object userToken = redisUtil.get(RedisKeys.getTokenKey(userId));
            if (userToken == null || "".equals(userToken)) {
                userToken = systemService.findToken(userId, new Date());
            }
            if (userToken == null || "".equals(userToken)) {
                return token(response, HttpStatus.UNAUTHORIZED);
            }
            if (!token.equals(userToken)) {
                return token(response, HttpStatus.UNAUTHORIZED);
            }

            UAUserDTO currentUser = systemService.getCurrentUser(request);
            if (currentUser == null) {
                return token(response, HttpStatus.UNAUTHORIZED);
            }
            if (currentUser.getParentId() != 0) {
                if (currentUser.getEnable() == 1) {
                    // 判断是否有权限访问
                    if (!isAuthorized(request, currentUser)) {
                        return token(response, HttpStatus.UNAUTHORIZED);
                    }
                } else {
                    return token(response, HttpStatus.UNAUTHORIZED);
                }
            }
            return chain.filter(exchange);
        };
    }

    public static class Config {
        // 控制是否开启认证
        private boolean enabled;

        public Config() {}

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }
    }

    private Mono<Void> token(ServerHttpResponse response, HttpStatus httpStatus) {
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    /**
     * 判断是否有访问权限
     */
    private boolean isAuthorized(ServerHttpRequest request, UAUserDTO currentUser) {
        String actionName = request.getURI().getPath().replace(request.getPath().contextPath().value(), "");
        actionName = actionName.replaceAll("\\+", "/");
        actionName = actionName.replaceAll("/+", "/");
        actionName = actionName.substring(1, actionName.lastIndexOf(".") > 0 ? actionName.lastIndexOf(".") : actionName.length());

        Object authUrl = redisUtil.get(AuthorityUtil.APPLICATION_COMMON_URL_KEY);
        if (authUrl instanceof Map) {
            @SuppressWarnings("unchecked")
            Map<Object, Object> commonUrl = (HashMap<Object, Object>) authUrl;
            if (commonUrl.get(actionName) != null && commonUrl.get(actionName).equals(1)) {
                return true;
            }
            Set<String> urlSet = currentUser.getUrlSet();
            if (urlSet == null || urlSet.isEmpty()) {
                return false;
            }
            return urlSet.contains(actionName);
        }
        return false;
    }

    /**
     * 判断是否是开放的访问地址
     */
    private boolean isExcludeUrl(String actionName) {
        if (actionName.equals("/favicon.ico"))
            return true;
        if (actionName.contains("/authorityLogin/"))
            return true;
        return actionName.contains("/error");
    }

}
