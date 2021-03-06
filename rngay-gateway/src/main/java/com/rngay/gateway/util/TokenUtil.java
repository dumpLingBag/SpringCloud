package com.rngay.gateway.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;

public class TokenUtil {

    public static String getRequestToken(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst("Authorization");
        if (StringUtils.isBlank(token)) {
            token = request.getQueryParams().getFirst("Authorization");
        }
        return token;
    }

}
