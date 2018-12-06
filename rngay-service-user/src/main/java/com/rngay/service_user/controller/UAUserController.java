package com.rngay.service_user.controller;

import com.rngay.common.vo.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "user")
public class UAUserController {

    @RequestMapping(value = "getUser")
    @ResponseBody
    public Result<?> getUser(){
        return Result.success();
    }

}
