package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.service_authority.service.UARoleMenuService;
import com.rngay.service_authority.service.UASystemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "authorityRoleMenu")
public class UARoleMenuController {

    @Resource
    private UASystemService systemService;
    @Resource
    private UARoleMenuService roleMenuService;

    @RequestMapping(value = "load")
    public Result<?> load(Integer roleId) {
        return Result.success(roleMenuService.load(roleId));
    }

    @RequestMapping(value = "loadMenu")
    public Result<?> loadMenu(HttpServletRequest request) {
        Integer orgId = systemService.getCurrentOrgId(request);

        return Result.success();
    }

}
