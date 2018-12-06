package com.rngay.feign.user;

import com.rngay.common.vo.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "service-user")
public interface UserService {

    @RequestMapping(value = "/user/getUser", method = RequestMethod.POST)
    Result<?> getUser();

}
