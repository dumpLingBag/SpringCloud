package com.rngay.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.user.dto.UAMemberDTO;
import com.rngay.feign.user.service.MemberService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "im")
@RestController
public class LoginController {

    @Autowired
    private MemberService memberService;

    @PostMapping(value = "login")
    public Result<?> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return Result.fail("用户名或密码不能为空");
        }

        UAMemberDTO member = memberService.findUser(username, password).getData();
        if (member == null) {
            return Result.fail("用户名或密码错误");
        }

        return Result.success();
    }

}
