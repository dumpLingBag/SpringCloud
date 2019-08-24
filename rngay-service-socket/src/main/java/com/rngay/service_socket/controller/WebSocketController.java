package com.rngay.service_socket.controller;

import com.riicy.common.util.CryptUtil;
import com.riicy.common.vo.Result;
import com.riicy.socket.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "socket")
public class WebSocketController {

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "alreadyRead", method = RequestMethod.POST)
    public Result<Integer> alreadyRead(@RequestParam("userId") String userId, @RequestParam("toUserId") String toUserId) {
        String toUserInfo = Integer.parseInt(userId) > Integer.parseInt(toUserId) ? toUserId + userId : userId + toUserId;
        String md5encrypt = CryptUtil.MD5encrypt(toUserInfo);
        return Result.success(messageService.alreadyRead(md5encrypt, null));
    }

    @RequestMapping(value = "getPhoto", method = RequestMethod.POST)
    public Result<?> getPhoto(@RequestParam("userId") String userId) {
        return Result.success(messageService.getPhoto(userId));
    }
}
