package com.rngay.feign.socket.fallback;

import com.rngay.common.vo.Result;
import com.rngay.feign.dto.PageQueryDTO;
import com.rngay.feign.socket.service.SocketService;
import feign.hystrix.FallbackFactory;

public class SocketServiceFallback implements FallbackFactory<SocketService> {
    @Override
    public SocketService create(Throwable throwable) {
        String result = "消息服务异常，请稍后再试";
        return new SocketService() {
            @Override
            public Result<?> sendUser(String content, String userId) {
                return Result.failMsg(result);
            }

            @Override
            public Result<?> sendAll(String content) {
                return Result.failMsg(result);
            }

            @Override
            public Result<?> getUser(PageQueryDTO queryDTO) {
                return Result.failMsg(result);
            }

            @Override
            public Result<?> kickOut(String userId) {
                return Result.failMsg(result);
            }

            @Override
            public Result<?> banned(String userId, Integer expire) {
                return Result.failMsg(result);
            }

            @Override
            public Result<?> getMessage(String userId) {
                return Result.failMsg(result);
            }
        };
    }
}
