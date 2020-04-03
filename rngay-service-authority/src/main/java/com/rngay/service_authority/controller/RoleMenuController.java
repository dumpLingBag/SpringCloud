package com.rngay.service_authority.controller;

import com.rngay.common.enums.FiledEnum;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.MenuDTO;
import com.rngay.feign.authority.RoleMenuDTO;
import com.rngay.feign.authority.query.ArrayQuery;
import com.rngay.feign.authority.query.UpdateRoleMenuQuery;
import com.rngay.service_authority.service.RoleMenuService;
import com.rngay.service_authority.service.SystemService;
import com.rngay.service_authority.util.MenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
        return Result.success(roleMenuService.list(orgId, FiledEnum.POWER));
    }

    @GetMapping(value = "listMenu")
    public Result<List<RoleMenuDTO>> listMenu(Long roleId) {
        if (roleId == null) {
            return Result.failMsg("加载菜单失败");
        }
        return Result.success(roleMenuService.listMenu(roleId));
    }

    @PostMapping(value = "insert")
    public Result<Boolean> save(@Valid @RequestBody UpdateRoleMenuQuery query) {
        return Result.success(roleMenuService.insert(query));
    }

    @PostMapping(value = "listAuth")
    public Result<List<MenuDTO>> listAuth(@Valid @RequestBody ArrayQuery arrayQuery) {
        return Result.success(MenuUtil.menuList(roleMenuService.listAuth(arrayQuery.getIds()), FiledEnum.MENU));
    }

}
