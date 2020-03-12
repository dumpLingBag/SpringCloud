package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.authority.UserRoleDTO;
import com.rngay.feign.authority.UserRoleParamDTO;
import com.rngay.feign.authority.UserRoleUpdateDTO;
import com.rngay.service_authority.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "authorityUserRole", name = "用户角色")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping(value = "load")
    public Result<List<UserRoleDTO>> load(Long userId) {
        if (userId != null) {
            return Result.success(userRoleService.load(userId));
        }
        return Result.failMsg("获取用户角色失败");
    }

    @ResponseBody
    @GetMapping(value = "loadUserByRoleId")
    public Result<List<UserRoleDTO>> loadUserByRoleId(@RequestBody UserRoleParamDTO userRoleParamDTO) {
        return Result.success();
    }

    @PutMapping(value = "update")
    public Result<Integer> update(@Valid @RequestBody UserRoleUpdateDTO updateDTO) {
        return Result.success(userRoleService.update(updateDTO));
    }

}
