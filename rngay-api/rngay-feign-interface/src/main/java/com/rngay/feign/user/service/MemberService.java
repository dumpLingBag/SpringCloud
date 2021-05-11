package com.rngay.feign.user.service;

import com.rngay.common.vo.Result;
import com.rngay.feign.user.dto.UaMemberDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-user")
public interface MemberService {

    @PostMapping(value = "/member/findUser")
    Result<UaMemberDTO> findUser(@RequestParam("username") String username);

}
