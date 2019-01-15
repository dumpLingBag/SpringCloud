package com.rngay.service_user.controller;

import com.rngay.common.vo.Result;
import com.rngay.service_user.service.UAIconService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "user")
public class PlatformIconController {

    @Resource
    private UAIconService uaIconService;

    @RequestMapping(value = "icon")
    public Result<List<Map<String, Object>>> loadIcon(){
        return Result.success(uaIconService.loadIcon());
    }

}
