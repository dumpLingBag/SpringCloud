package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.platform.UpdateRoleMenuDTO;
import com.rngay.service_authority.service.UARoleMenuService;
import com.rngay.service_authority.service.UASystemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "authorityRoleMenu")
public class UARoleMenuController {

    @Resource
    private UASystemService systemService;
    @Resource
    private UARoleMenuService roleMenuService;

    @RequestMapping(value = "load", method = RequestMethod.GET)
    public Result<?> load(HttpServletRequest request) {
        Integer orgId = systemService.getCurrentOrgId(request);
        return Result.success(roleMenuService.load(orgId));
    }

    @RequestMapping(value = "loadMenu", method = RequestMethod.GET)
    public Result<?> loadMenu(Integer roleId) {
        if (roleId == null) {
            return Result.failMsg("加载菜单失败");
        }
        return Result.success(roleMenuService.loadMenu(roleId));
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result<?> update(@Valid @RequestBody UpdateRoleMenuDTO roleMenu) {
        return Result.success(roleMenuService.update(roleMenu));
    }

}
