package com.rngay.service_authority.controller;

import com.rngay.common.aspect.annotation.RepeatSubmit;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.RoleDTO;
import com.rngay.feign.authority.query.RoleIdListQuery;
import com.rngay.feign.authority.RoleInListDTO;
import com.rngay.service_authority.service.RoleService;
import com.rngay.service_authority.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "role")
public class RoleController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private RoleService roleService;

    @GetMapping(value = "list")
    public Result<List<RoleDTO>> list(HttpServletRequest request) {
        Long orgId = systemService.getCurrentOrgId(request);
        if (orgId == null) {
            return Result.failMsg("角色查询失败");
        }
        return Result.success(roleService.list(orgId));
    }

    @GetMapping(value = "listRole")
    public Result<List<RoleDTO>> listRole(HttpServletRequest request) {
        Long orgId = systemService.getCurrentOrgId(request);
        if (orgId == null) {
            return Result.failMsg("角色查询失败");
        }
        return Result.success(roleService.listRole(orgId));
    }

    @GetMapping(value = "listByPid")
    public Result<List<RoleDTO>> listByPid(HttpServletRequest request) {
        Long orgId = systemService.getCurrentOrgId(request);
        if (orgId != null) {
            return Result.success(roleService.listByPid(orgId));
        }
        return Result.failMsg("角色加载失败");
    }

    @RepeatSubmit
    @PostMapping(value = "insert")
    public Result<Integer> insert(HttpServletRequest request, @RequestBody RoleDTO uaRole) {
        Long orgId = systemService.getCurrentOrgId(request);
        if (orgId == null) {
            return Result.failMsg("角色添加失败");
        }
        uaRole.setOrgId(orgId);
        boolean save = roleService.save(uaRole);
        if (!save) {
            return Result.failMsg("角色添加失败");
        }
        return Result.success();
    }

    @PutMapping(value = "update")
    public Result<Integer> update(@RequestBody RoleDTO uaRole) {
        boolean role = roleService.updateById(uaRole);
        if (!role) {
            return Result.failMsg("角色修改失败");
        }
        return Result.success();
    }

    @DeleteMapping(value = "delete")
    public Result<Integer> delete(@Valid @RequestBody RoleIdListQuery listDTO) {
        return Result.success(roleService.delete(listDTO));
    }

    @PutMapping(value = "updateInList")
    public Result<Integer> updateInList(@RequestBody RoleInListDTO roleInList) {
        if (roleInList == null || roleInList.getRoleIdList().isEmpty()) {
            return Result.failMsg("角色更新失败");
        }
        return Result.success(roleService.updateInList(roleInList));
    }

}
