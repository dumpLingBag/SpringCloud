package com.rngay.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.dto.PageQueryDTO;
import com.rngay.feign.socket.dto.PageMessageDTO;
import com.rngay.feign.socket.service.SocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "socket", name = "消息通知")
public class WebSocketController {

    @Autowired
    private SocketService socketService;

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

    @RequestMapping(value = "getMessage", method = RequestMethod.GET, name = "获取聊天记录")
    public Result<?> getMessage(@RequestBody PageMessageDTO messageDTO) {
        return socketService.getMessage(messageDTO);
    }

    @RequestMapping(value = "getCacheMessage", method = RequestMethod.GET, name = "获取未读消息")
    public Result<?> getCacheMessage(@RequestParam("sendUserId") String sendUserId,
                                     @RequestParam("receiveUserId") String receiveUserId) {
        return socketService.getCacheMessage(sendUserId, receiveUserId);
    }

}
