package com.rngay.feign.socket.fallback;

import com.rngay.feign.socket.service.SocketService;
import feign.hystrix.FallbackFactory;

public class SocketServiceFallback implements FallbackFactory<SocketService> {

    @Override
    public SocketService create(Throwable throwable) {
        return null;
    }
}
