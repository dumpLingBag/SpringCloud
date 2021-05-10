package com.rngay.authority.controller;

import com.rngay.authority.constant.Constant;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.MenuDTO;
import com.rngay.feign.authority.RoleMenuDTO;
import com.rngay.feign.authority.query.UpdateRoleMenuQuery;
import com.rngay.authority.service.RoleMenuService;
import com.rngay.authority.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "roleMenu")
public class RoleMenuController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private RoleMenuService roleMenuService;

    @GetMapping(value = "list")
    public Result<List<MenuDTO>> list() {
        Long orgId = systemService.getCurrentOrgId();
        return Result.success(roleMenuService.list(orgId, Constant.POWER));
    }

    @GetMapping(value = "listCheckMenu")
    public Result<List<Long>> listCheckMenu(Long roleId) {
        if(roleId == null) {
            return Result.failMsg("缺少参数");
        }
        List<Long> checked = new ArrayList<>();
        List<RoleMenuDTO> roleMenu = roleMenuService.listCheckMenu(roleId);
        if (roleMenu != null && !roleMenu.isEmpty()) {
            roleMenu.forEach(key -> checked.add(key.getMenuId()));
        }
        return Result.success(checked);
    }

    @PostMapping(value = "insert")
    public Result<Boolean> save(@Valid @RequestBody UpdateRoleMenuQuery query) {
        return Result.success(roleMenuService.insert(query));
    }

}
