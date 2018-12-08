package com.rngay.service_authority.controller;

import com.rngay.service_authority.service.UserService;
import com.rngay.common.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "user")
public class UserLogin {

    @Resource
    private UserService userService;

    @RequestMapping(value = "login")
    @ResponseBody
    public Result<?> login(){
        Map<String, Object> map = new HashMap<>();
        map.put("account","qwer");
        map.put("name","ooooo");
        int i = userService.saveUser(map);
        return Result.success(i);
    }

}
