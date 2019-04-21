package com.rngay.feign.socket.service;

import com.rngay.common.vo.Result;
import com.rngay.feign.dto.PageQueryDTO;
import com.rngay.feign.socket.dto.SendAllDTO;
import com.rngay.feign.socket.dto.SendUserDTO;
import com.rngay.feign.socket.fallback.SocketServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "service-socket", fallbackFactory = SocketServiceFallback.class)
public interface SocketService {

    @RequestMapping(value = "/socket/sendUser", method = RequestMethod.POST)
    Result<?> sendUser(@RequestParam("content") String content, @RequestParam("userId") String userId);

    @RequestMapping(value = "/socket/sendAll", method = RequestMethod.POST)
    Result<?> sendAll(@RequestParam("content") String content);

    @RequestMapping(value = "/socket/getUser", method = RequestMethod.POST)
    Result<?> getUser(@RequestBody PageQueryDTO queryDTO);

    @RequestMapping(value = "/socket/kickOut", method = RequestMethod.GET)
    Result<?> kickOut(@RequestParam("userId") String userId);

}
