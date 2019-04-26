package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.platform.UserRoleUpdateDTO;
import com.rngay.service_authority.service.UAUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "authorityUserRole", name = "用户角色")
public class UAUserRoleController {

    @Autowired
    private UAUserRoleService userRoleService;

    @RequestMapping(value = "load", method = RequestMethod.GET, name = "加载用户的权限")
    public Result<?> load(Integer userId) {
        if (userId != null) {
            return Result.success(userRoleService.load(userId));
        }
        return Result.failMsg("获取用户角色失败");
    }

    @RequestMapping(value = "update", method = RequestMethod.POST, name = "更新用户角色")
    public Result<?> update(@Valid @RequestBody UserRoleUpdateDTO updateDTO) {
        return Result.success(userRoleService.update(updateDTO));
    }

}
