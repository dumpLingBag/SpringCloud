package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.authority.UpdateRoleMenuDTO;
import com.rngay.service_authority.service.RoleMenuService;
import com.rngay.service_authority.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "authorityRoleMenu", name = "角色菜单")
public class RoleMenuController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private RoleMenuService roleMenuService;

    @RequestMapping(value = "load", method = RequestMethod.GET, name = "根据orgId加载关联的菜单")
    public Result<?> load(HttpServletRequest request) {
        Integer orgId = systemService.getCurrentOrgId(request);
        return Result.success(roleMenuService.load(orgId));
    }

    @RequestMapping(value = "loadMenu", method = RequestMethod.GET, name = "根据roleId加载菜单")
    public Result<?> loadMenu(Integer roleId) {
        if (roleId == null) {
            return Result.failMsg("加载菜单失败");
        }
        return Result.success(roleMenuService.loadMenu(roleId));
    }

    @RequestMapping(value = "update", method = RequestMethod.POST, name = "更新角色选择菜单")
    public Result<?> update(@Valid @RequestBody UpdateRoleMenuDTO roleMenu) {
        return Result.success(roleMenuService.update(roleMenu));
    }

}
