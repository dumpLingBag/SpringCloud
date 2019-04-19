package com.rngay.feign.socket.service;

import com.rngay.common.vo.Result;
import com.rngay.feign.dto.PageQueryDTO;
import com.rngay.feign.socket.dto.SendAllDTO;
import com.rngay.feign.socket.dto.SendUserDTO;
import com.rngay.feign.socket.fallback.SocketServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "service-socket", fallbackFactory = SocketServiceFallback.class)
public interface SocketService {

    @RequestMapping(value = "socket/sendUser", method = RequestMethod.POST)
    Result<?> sendUser(@RequestBody SendUserDTO sendUserDTO);

    @RequestMapping(value = "socket/sendAll", method = RequestMethod.POST)
    Result<?> sendAll(@RequestBody SendAllDTO sendAllDTO);

    @RequestMapping(value = "socket/getUser", method = RequestMethod.POST)
    Result<?> getUser(@RequestBody PageQueryDTO queryDTO);

}
