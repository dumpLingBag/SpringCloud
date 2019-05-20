package com.rngay.service_user.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.user.dto.UAIconDTO;
import com.rngay.service_user.service.UAIconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "user")
public class PlatformIconController {

    @Autowired
    private UAIconService uaIconService;

    @RequestMapping(value = "icon", method = RequestMethod.GET)
    public Result<List<UAIconDTO>> loadIcon() {
        return Result.success(uaIconService.loadIcon());
    }

}
