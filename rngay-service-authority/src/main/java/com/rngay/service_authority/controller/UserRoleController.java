package com.rngay.service_authority.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.UserRoleDTO;
import com.rngay.feign.authority.UserRoleParamDTO;
import com.rngay.feign.authority.query.UserRoleUpdateQuery;
import com.rngay.feign.user.dto.UaUserDTO;
import com.rngay.service_authority.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "userRole", name = "用户角色")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping(value = "list")
    public Result<List<UserRoleDTO>> list(Long userId) {
        if (userId != null) {
            return Result.success(userRoleService.list(userId));
        }
        return Result.failMsg("获取用户角色失败");
    }

    @GetMapping(value = "pageUserByRoleId")
    public Result<Page<UaUserDTO>> pageUserByRoleId(UserRoleParamDTO userRole) {
        return Result.success(userRoleService.pageUserByRoleId(userRole));
    }

    @PostMapping(value = "insert")
    public Result<Boolean> insert(@Valid @RequestBody UserRoleUpdateQuery query) {
        return Result.success(userRoleService.insert(query));
    }

}
