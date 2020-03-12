package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.authority.MenuDTO;
import com.rngay.feign.authority.RoleMenuDTO;
import com.rngay.feign.authority.UpdateRoleMenuDTO;
import com.rngay.service_authority.service.RoleMenuService;
import com.rngay.service_authority.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "authorityRoleMenu")
public class RoleMenuController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private RoleMenuService roleMenuService;

    @GetMapping(value = "load")
    public Result<List<MenuDTO>> load(HttpServletRequest request) {
        Long orgId = systemService.getCurrentOrgId(request);
        return Result.success(roleMenuService.load(orgId));
    }

    @GetMapping(value = "loadMenu")
    public Result<List<RoleMenuDTO>> loadMenu(Integer roleId) {
        if (roleId == null) {
            return Result.failMsg("加载菜单失败");
        }
        return Result.success(roleMenuService.loadMenu(roleId));
    }

    @PutMapping(value = "update")
    public Result<Integer> update(@Valid @RequestBody UpdateRoleMenuDTO roleMenu) {
        return Result.success(roleMenuService.update(roleMenu));
    }

}
