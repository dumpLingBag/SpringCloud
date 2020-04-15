package com.rngay.gateway.filter;

import com.rngay.gateway.util.TokenUtil;
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
     * 判断是否是开放的访问地址
     */
    private boolean isExcludeUrl(String actionName) {
        if (actionName.equals("/favicon.ico")) {
            return true;
        }
        if (actionName.contains("/login")) {
            return true;
        }
        if (actionName.contains("/login/captcha")) {
            return true;
        }
        return actionName.contains("/error");
    }

}
