package com.rngay.feign.socket.service;

import com.rngay.feign.socket.fallback.SocketServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "service-socket", fallbackFactory = SocketServiceFallback.class)
public interface SocketService {

}
