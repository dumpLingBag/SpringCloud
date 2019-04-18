package com.rngay.socket.controller;

import com.rngay.common.vo.Result;
import com.rngay.socket.NettyWebSocket;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "socket")
public class WebSocketController {

    @Resource
    private NettyWebSocket nettyWebSocket;

    @RequestMapping(value = "sendUser", method = RequestMethod.GET)
    public Result<?> sendUser(@RequestParam("userId") String userId, @RequestParam("message") String message) {
        nettyWebSocket.sendUser(message, userId);
        return Result.success();
    }

    @RequestMapping(value = "sendAll", method = RequestMethod.GET)
    public Result<?> sendAll(@RequestParam("message") String message) {
        nettyWebSocket.sendAll(message);
        return Result.success();
    }

}
