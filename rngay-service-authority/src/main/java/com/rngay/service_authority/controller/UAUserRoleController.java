package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.platform.UserRoleUpdateDTO;
import com.rngay.service_authority.service.UAUserRoleService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "authorityUserRole")
public class UAUserRoleController {

    @Resource
    private UAUserRoleService userRoleService;

    @RequestMapping(value = "load")
    public Result<?> load(Integer userId) {
        if (userId != null) {
            return Result.success(userRoleService.load(userId));
        }
        return Result.failMsg("获取用户角色失败");
    }

    @RequestMapping(value = "update")
    public Result<?> update(@Valid @RequestBody UserRoleUpdateDTO updateDTO) {
        return Result.success(userRoleService.update(updateDTO));
    }

}
