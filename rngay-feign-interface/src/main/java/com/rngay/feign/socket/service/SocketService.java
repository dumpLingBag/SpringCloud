package com.rngay.feign.socket.service;

import com.rngay.common.vo.Result;
import com.rngay.feign.dto.PageQueryDTO;
import com.rngay.feign.socket.fallback.SocketServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "service-socket", fallbackFactory = SocketServiceFallback.class)
public interface SocketService {

    /**
    * 给指定用户发送消息
    * @Author pengcheng
    * @Date 2019/4/25
    **/
    @RequestMapping(value = "/socket/sendUser", method = RequestMethod.POST)
    Result<?> sendUser(@RequestParam("content") String content, @RequestParam("userId") String userId);

    /**
    * 给所有用户发送消息
    * @Author pengcheng
    * @Date 2019/4/25
    **/
    @RequestMapping(value = "/socket/sendAll", method = RequestMethod.POST)
    Result<?> sendAll(@RequestParam("content") String content);

    /**
    * 分页取出缓存中的用户信息
    * @Author pengcheng
    * @Date 2019/4/25
    **/
    @RequestMapping(value = "/socket/getUser", method = RequestMethod.POST)
    Result<?> getUser(@RequestBody PageQueryDTO queryDTO);

    /**
    * 踢指定用户下线
    * @Author pengcheng
    * @Date 2019/4/25
    **/
    @RequestMapping(value = "/socket/kickOut", method = RequestMethod.GET)
    Result<?> kickOut(@RequestParam("userId") String userId);

    /**
    * 禁言指定用户
    * @Author pengcheng
    * @Date 2019/4/25
    **/
    @RequestMapping(value = "/socket/banned", method = RequestMethod.POST)
    Result<?> banned(@RequestParam("userId") String userId, @RequestParam("expire") Integer expire);

    /**
    * 获取指定用户发送的消息内容
    * @Author pengcheng
    * @Date 2019/4/25
    **/
    @RequestMapping(value = "/socket/getMessage", method = RequestMethod.GET)
    Result<?> getMessage(@RequestParam("userId") String userId);

}
