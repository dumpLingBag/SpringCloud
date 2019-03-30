package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.service_authority.model.UARole;
import com.rngay.service_authority.service.UARoleService;
import com.rngay.service_authority.service.UASystemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "authorityRole")
public class UARoleController {

    @Resource
    private UASystemService systemService;
    @Resource
    private UARoleService roleService;

    @RequestMapping(value = "load")
    public Result<?> load(HttpServletRequest request) {
        Integer orgId = systemService.getCurrentOrgId(request);
        return Result.success(roleService.load(orgId));
    }

    @RequestMapping(value = "save")
    public Result<?> save(HttpServletRequest request, @RequestBody UARole uaRole) {
        Integer orgId = systemService.getCurrentOrgId(request);
        if (orgId == null) {
            return Result.failMsg("角色添加失败");
        }
        uaRole.setOrgId(orgId);
        Integer role = roleService.save(uaRole);
        if (role == null) {
            return Result.failMsg("角色添加失败");
        }
        return Result.success();
    }

    @RequestMapping(value = "update")
    public Result<?> update(@RequestBody UARole uaRole) {
        Integer role = roleService.update(uaRole);
        if (role == null) {
            return Result.failMsg("角色修改失败");
        }
        return Result.success();
    }

}
