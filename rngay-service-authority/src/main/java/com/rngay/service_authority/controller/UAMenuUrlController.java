package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.service_authority.service.UAMenuUrlService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "authorityMenuUrl")
public class UAMenuUrlController {

    @Resource
    private UAMenuUrlService urlService;

    @RequestMapping("load")
    public Result<?> load() {
        return Result.success(urlService.load());
    }

    @RequestMapping("loadUrl")
    public Result<?> loadUrl(Integer id) {
        if (id != null) {
            return Result.success(urlService.loadUrl(id));
        }
        return Result.fail("缺少 {id} 参数");
    }

}
