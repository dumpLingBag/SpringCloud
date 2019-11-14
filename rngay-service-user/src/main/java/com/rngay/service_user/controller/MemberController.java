package com.rngay.service_user.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.user.dto.UAMemberDTO;
import com.rngay.service_user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "member")
@RestController
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping(value = "findUser")
    public Result<UAMemberDTO> findUser(@RequestParam("username") String username) {
        return Result.success(memberService.findUser(username));
    }

}
