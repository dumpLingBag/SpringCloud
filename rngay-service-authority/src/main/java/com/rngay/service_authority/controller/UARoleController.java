package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.platform.RoleIdListDTO;
import com.rngay.service_authority.model.UARole;
import com.rngay.service_authority.service.UARoleService;
import com.rngay.service_authority.service.UASystemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "authorityRole", name = "角色管理")
public class UARoleController {

    @Resource
    private UASystemService systemService;
    @Resource
    private UARoleService roleService;

    @RequestMapping(value = "load", method = RequestMethod.GET, name = "加载角色")
    public Result<?> load(HttpServletRequest request) {
        Integer orgId = systemService.getCurrentOrgId(request);
        if (orgId == null) {
            return Result.failMsg("角色查询失败");
        }
        return Result.success(roleService.load(orgId));
    }

    @RequestMapping(value = "loadByPid", method = RequestMethod.GET, name = "获取pid为0的角色")
    public Result<?> loadByPid(HttpServletRequest request) {
        Integer orgId = systemService.getCurrentOrgId(request);
        if (orgId != null) {
            return Result.success(roleService.loadByPid(orgId));
        }
        return Result.failMsg("角色加载失败");
    }

    @RequestMapping(value = "save", method = RequestMethod.POST, name = "添加角色")
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
        return Result.success(role);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST, name = "修改角色")
    public Result<?> update(@RequestBody UARole uaRole) {
        Integer role = roleService.update(uaRole);
        if (role == null) {
            return Result.failMsg("角色修改失败");
        }
        return Result.success(role);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST, name = "删除角色")
    public Result<?> delete(@RequestBody RoleIdListDTO listDTO) {
        if (listDTO.getRoleIdList() == null || listDTO.getRoleIdList().isEmpty()) {
            return Result.failMsg("删除角色失败");
        }
        return Result.success(roleService.delete(listDTO));
    }

}
