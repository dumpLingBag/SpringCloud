package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.dto.PageQueryDTO;
import com.rngay.feign.socket.service.SocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "socket", name = "消息通知")
public class WebSocketController {

    @Autowired
    private SocketService socketService;

    @RequestMapping(value = "sendUser", method = RequestMethod.POST, name = "给指定用户发消息")
    public Result<?> sendUser(@RequestParam("content") String content, @RequestParam("userId") String userId) {
        return socketService.sendUser(content, userId);
    }

    @RequestMapping(value = "sendAll", method = RequestMethod.POST, name = "给所有用户发消息")
    public Result<?> sendAll(@RequestParam("content") String content) {
        return socketService.sendAll(content);
    }

    @RequestMapping(value = "getUser", method = RequestMethod.POST, name = "获取在线用户")
    public Result<?> getUser(@Valid @RequestBody PageQueryDTO queryDTO) {
        return socketService.getUser(queryDTO);
    }

    @RequestMapping(value = "kickOut", method = RequestMethod.GET, name = "踢用户下线")
    public Result<?> kickOut(@RequestParam("userId") String userId) {
        return socketService.kickOut(userId);
    }

    @RequestMapping(value = "banned", method = RequestMethod.POST, name = "禁言")
    public Result<?> banned(@RequestParam("userId") String userId, @RequestParam("expire") Integer expire) {
        return socketService.banned(userId, expire);
    }

}
