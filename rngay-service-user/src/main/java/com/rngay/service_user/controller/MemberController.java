package com.rngay.service_user.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.user.dto.UAMemberDTO;
import com.rngay.service_user.service.UAMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "member")
@RestController
public class MemberController {

    @Autowired
    private UAMemberService uaMemberService;

    @PostMapping(value = "findUser")
    public Result<UAMemberDTO> findUser(@RequestParam("account") String account) {
        return Result.success(uaMemberService.findUser(account));
    }

}
