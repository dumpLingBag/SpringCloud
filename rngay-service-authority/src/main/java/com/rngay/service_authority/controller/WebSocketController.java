package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.dto.PageQueryDTO;
import com.rngay.feign.socket.service.SocketService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "socket")
public class WebSocketController {

    @Resource
    private SocketService socketService;

    @RequestMapping(value = "getUser", method = RequestMethod.POST)
    public Result<?> getUser(@RequestBody PageQueryDTO queryDTO) {
        return socketService.getUser(queryDTO);
    }

}
