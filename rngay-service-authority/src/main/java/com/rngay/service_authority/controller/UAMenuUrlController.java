package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "authorityMenuUrl")
public class UAMenuUrlController {

    @RequestMapping("load")
    public Result<?> load() {

        return Result.success();
    }

}
