package com.rngay.service_socket.controller;

import com.rngay.common.cache.RedisUtil;
import com.rngay.common.vo.PageList;
import com.rngay.common.vo.Result;
import com.rngay.feign.dto.PageQueryDTO;
import com.rngay.service_socket.NettyWebSocket;
import com.rngay.service_socket.contants.RedisKeys;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "socket")
public class WebSocketController {

    @Resource
    private NettyWebSocket nettyWebSocket;
    @Resource
    private RedisUtil redisUtil;

    @RequestMapping(value = "sendUser", method = RequestMethod.POST)
    public Result<String> sendUser(@RequestParam("content") String content, @RequestParam("userId") String userId) {
        Object o = redisUtil.get(RedisKeys.getBanned(userId));
        if (o != null) {
            return Result.failMsg("该用户已被禁言");
        }
        boolean b = nettyWebSocket.sendUser(content, userId);
        return Result.success(b ? "发送成功" : "该账号已下线");
    }

    /*
    * 禁言用户没下线也能接收到群发消息
    * */
    @RequestMapping(value = "sendAll", method = RequestMethod.POST)
    public Result<String> sendAll(@RequestParam("content") String content) {
        boolean b = nettyWebSocket.sendAll(content);
        return Result.success(b ? "群发消息成功" : "暂时没有用户在线");
    }

    @RequestMapping(value = "getUser", method = RequestMethod.POST)
    public Result<PageList<?>> getUser(@RequestBody PageQueryDTO queryDTO) {
        Set<Object> user = redisUtil.range(RedisKeys.KEY_SET_USER, 0, -1);
        if (user.size() > 0) {
            List<Object> list = new ArrayList<>(user);
            PageList<Object> pageList = new PageList<>(queryDTO.getCurrentPage(), queryDTO.getPageSize(), list.size());
            pageList.setList(list);
            return Result.success(pageList);
        }
        return Result.success(new PageList<>());
    }

    @RequestMapping(value = "getExpire", method = RequestMethod.GET)
    public Result<Long> getExpire(@RequestParam("userId") String userId) {
        if (userId != null && !"".equals(userId)) {
            return Result.success(redisUtil.getExpire(RedisKeys.getMessage(userId)));
        }
        return Result.failMsg("用户ID为空");
    }

    @RequestMapping(value = "getMessage", method = RequestMethod.GET)
    public Result<Set> getMessage(@RequestParam("userId") String userId) {
        if (userId != null && !"".equals(userId)) {
            Set<Object> range = redisUtil.range(RedisKeys.getMessage(userId), 0, -1);
            return Result.success(range);
        }
        return Result.failMsg("用户ID为空");
    }

    @RequestMapping(value = "kickOut", method = RequestMethod.GET)
    public Result<String> kickOut(@RequestParam("userId") String userId) {
        if (userId != null) {
            boolean b = nettyWebSocket.kickOut(userId);
            return Result.success(b ? "成功将用户踢下线" : "该用户已下线");
        }
        return Result.failMsg("操作失败");
    }

    @RequestMapping(value = "banned", method = RequestMethod.POST)
    public Result<?> banned(@RequestParam("userId") String userId, @RequestParam("expire") Integer expire) {
        if (userId != null) {
            redisUtil.set(RedisKeys.getBanned(userId), userId, expire == null ? 1000 * 60 * 30 : expire);
            return Result.success();
        }
        return Result.failMsg("禁言用户失败");
    }

}
