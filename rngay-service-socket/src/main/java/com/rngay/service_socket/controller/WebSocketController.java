package com.rngay.service_socket.controller;

import com.rngay.common.vo.PageList;
import com.rngay.common.vo.Result;
import com.rngay.feign.dto.PageQueryDTO;
import com.rngay.feign.user.dto.UAUserDTO;
import com.rngay.feign.user.service.PFUserService;
import com.rngay.service_socket.NettyWebSocket;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping(value = "socket")
public class WebSocketController {

    @Resource
    private NettyWebSocket nettyWebSocket;
    @Resource
    private PFUserService userService;

    @RequestMapping(value = "sendUser", method = RequestMethod.POST)
    public Result<String> sendUser(@RequestParam("content") String content, @RequestParam("userId") String userId) {
        boolean b = nettyWebSocket.sendUser(content, userId);
        return Result.success(b ? "发送成功" : "该账号已下线");
    }

    @RequestMapping(value = "sendAll", method = RequestMethod.POST)
    public Result<String> sendAll(@RequestParam("content") String content) {
        boolean b = nettyWebSocket.sendAll(content);
        return Result.success(b ? "群发消息成功" : "暂时没有用户在线");
    }

    @RequestMapping(value = "getUser", method = RequestMethod.POST)
    public Result<PageList<UAUserDTO>> getUser(@RequestBody PageQueryDTO queryDTO) {
        List<String> user = nettyWebSocket.getUser();
        if (user != null && !user.isEmpty()) {
            int current = (queryDTO.getCurrentPage() - 1) * queryDTO.getPageSize() - 1;
            current = current <= 0 ? 0 : current;

            int pageSize = current + (queryDTO.getPageSize() - 1);
            List<String> list = user.size() >= pageSize ? user.subList(current, pageSize) : user;

            Result<List<UAUserDTO>> listResult = userService.noticeUserList(list);
            PageList<UAUserDTO> pageList = new PageList<>(queryDTO.getCurrentPage(), queryDTO.getPageSize(), user.size());
            pageList.setList(listResult.getData());
            return Result.success(pageList);
        }
        return Result.success(new PageList<>());
    }

    @RequestMapping(value = "kickOut", method = RequestMethod.GET)
    public Result<String> kickOut(@RequestParam("userId") String userId) {
        if (userId != null) {
            boolean b = nettyWebSocket.kickOut(userId);
            return Result.success(b ? "成功将用户踢下线" : "该用户已下线");
        }
        return Result.failMsg("请选择要踢出的用户");
    }

}
