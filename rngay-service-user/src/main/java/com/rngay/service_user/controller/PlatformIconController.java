package com.rngay.service_user.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.user.dto.UaIconDTO;
import com.rngay.service_user.service.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "user")
public class PlatformIconController {

    @Autowired
    private IconService iconService;

    @GetMapping(value = "icon")
    public Result<List<UaIconDTO>> loadIcon() {
        return Result.success(iconService.loadIcon());
    }

}
