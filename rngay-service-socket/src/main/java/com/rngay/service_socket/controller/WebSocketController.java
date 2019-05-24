package com.rngay.service_socket.controller;

import com.rngay.common.cache.RedisUtil;
import com.rngay.common.vo.PageList;
import com.rngay.common.vo.Result;
import com.rngay.feign.dto.PageQueryDTO;
import com.rngay.feign.socket.dto.PageMessageDTO;
import com.rngay.service_socket.NettyWebSocket;
import com.rngay.service_socket.contants.Contants;
import com.rngay.service_socket.contants.RedisKeys;
import com.rngay.service_socket.util.MessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "socket")
public class WebSocketController {

    @Autowired
    private NettyWebSocket nettyWebSocket;
    @Autowired
    private RedisUtil redisUtil;

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
        Integer currentPage = queryDTO.getCurrentPage();
        Integer pageSize = queryDTO.getPageSize();
        currentPage = (currentPage - 1) * pageSize;
        pageSize = currentPage == 0 ? pageSize - 1 : currentPage * pageSize - 1;
        Set<Object> user = redisUtil.range(RedisKeys.KEY_SET_USER, currentPage, pageSize);
        if (user.size() > 0) {
            List<Object> list = new ArrayList<>(user);
            PageList<Object> pageList = new PageList<>(queryDTO.getCurrentPage(), queryDTO.getPageSize(), list.size());
            pageList.setList(list);
            return Result.success(pageList);
        }
        return Result.success(new PageList<>());
    }

    @RequestMapping(value = "getMessage", method = RequestMethod.GET)
    public Result<PageList<?>> getMessage(@RequestBody PageMessageDTO messageDTO) {
        if (!StringUtils.isEmpty(messageDTO.getSupplierId()) && !StringUtils.isEmpty(messageDTO.getMemberId())) {
            List<Integer> sort = MessageUtil.sort(messageDTO.getSupplierId(), messageDTO.getMemberId());
            Long zCard = redisUtil.zCard(RedisKeys.getMessage(sort));
            if (zCard > 0) {
                Integer currentPage = messageDTO.getCurrentPage();
                Integer pageSize = messageDTO.getPageSize();
                currentPage = (currentPage - 1) * pageSize;
                pageSize = currentPage == 0 ? pageSize - 1 : currentPage * pageSize - 1;
                Set<Object> range = redisUtil.reverseRange(RedisKeys.getMessage(sort), currentPage, pageSize);
                List<Object> list = new ArrayList<>(range);
                PageList<Object> tPageList = new PageList<>(messageDTO.getCurrentPage(), zCard, messageDTO.getPageSize());
                tPageList.setList(list);
                return Result.success(tPageList);
            }
            return Result.success(new PageList<>());
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
            redisUtil.set(RedisKeys.getBanned(userId), userId, expire == null ? 60 * 30 : expire);
            return Result.success();
        }
        return Result.failMsg("禁言用户失败");
    }

    @RequestMapping(value = "getCacheMessage", method = RequestMethod.GET)
    public Result<?> getCacheMessage(@RequestParam("sendUserId") String sendUserId, @RequestParam("receiveUserId") String receiveUserId) {
        List<Integer> sort = MessageUtil.sort(sendUserId, receiveUserId);
        Set<Object> range = redisUtil.range(RedisKeys.getCacheMessage(sort), 0, -1);
        if (range != null && range.size() > 0) {
            for (Object ob : range) {
                redisUtil.zadd(RedisKeys.getMessage(sort), new Date().getTime(), ob);
            }
            redisUtil.expire(RedisKeys.getMessage(sort), Contants.EXPiRE_MESSAGE);
            redisUtil.del(RedisKeys.getCacheMessage(sort));
        }
        return Result.success(range);
    }

}
