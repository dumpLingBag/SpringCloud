package com.rngay.feign.socket.fallback;

import com.rngay.common.vo.Result;
import com.rngay.feign.socket.dto.SendAllDTO;
import com.rngay.feign.socket.dto.SendUserDTO;
import com.rngay.feign.socket.service.SocketService;
import feign.hystrix.FallbackFactory;

public class SocketServiceFallback implements FallbackFactory<SocketService> {
    @Override
    public SocketService create(Throwable throwable) {
        String result = "消息服务异常，请稍后再试";
        return new SocketService() {
            @Override
            public Result<?> sendUser(SendUserDTO sendUserDTO) {
                return Result.failMsg(result);
            }

            @Override
            public Result<?> sendAll(SendAllDTO sendAllDTO) {
                return Result.failMsg(result);
            }
        };
    }
}
