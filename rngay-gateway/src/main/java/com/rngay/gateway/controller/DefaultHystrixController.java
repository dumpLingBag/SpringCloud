package com.rngay.gateway.controller;

import com.rngay.common.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultHystrixController {

    @RequestMapping("/defaultFallback")
    public Result<String> defaultFallback(){
        return Result.fail(-1, "服务异常，请稍后再试");
    }

}
