package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.dto.PageQueryDTO;
import com.rngay.feign.socket.service.SocketService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "socket")
public class WebSocketController {


    @Resource
    private SocketService socketService;

    @RequestMapping(value = "sendUser", method = RequestMethod.POST)
    public Result<?> sendUser(@RequestParam("content") String content, @RequestParam("userId") String userId) {
        return socketService.sendUser(content, userId);
    }

    @RequestMapping(value = "getUser", method = RequestMethod.POST)
    public Result<?> getUser(@RequestBody PageQueryDTO queryDTO) {
        return socketService.getUser(queryDTO);
    }

    @RequestMapping(value = "kickOut", method = RequestMethod.GET)
    public Result<?> kickOut(@RequestParam("userId") String userId) {
        return socketService.kickOut(userId);
    }

}
