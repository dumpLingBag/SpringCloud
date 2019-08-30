package com.rngay.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.user.dto.UAMemberDTO;
import com.rngay.feign.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping(value = "save")
    public Result<?> save(@RequestBody UAMemberDTO memberDTO) {
        return Result.success();
    }

}
