package com.rngay.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.dto.PageQueryDTO;
import com.rngay.feign.socket.dto.ContentDTO;
import com.rngay.feign.socket.dto.MessageDTO;
import com.rngay.feign.socket.service.SocketService;
import com.rngay.feign.user.dto.UAUserDTO;
import com.rngay.service_authority.service.UASystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping(value = "socket", name = "消息通知")
public class WebSocketController {

    @Autowired
    private SocketService socketService;
    @Autowired
    private UASystemService systemService;

    @RequestMapping(value = "sendUser", method = RequestMethod.POST, name = "给指定用户发消息")
    public Result<?> sendUser(HttpServletRequest request,@Valid @RequestBody ContentDTO contentDTO) {
        UAUserDTO currentUser = systemService.getCurrentUser(request);
        contentDTO.setFm(String.valueOf(currentUser.getId()));
        return socketService.sendUser(contentDTO);
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
