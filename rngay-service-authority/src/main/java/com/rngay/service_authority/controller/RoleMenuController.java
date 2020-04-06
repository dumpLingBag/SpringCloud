package com.rngay.service_authority.controller;

import com.rngay.common.enums.FiledEnum;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.MenuDTO;
import com.rngay.feign.authority.RoleMenuDTO;
import com.rngay.feign.authority.query.ArrayQuery;
import com.rngay.feign.authority.query.UpdateRoleMenuQuery;
import com.rngay.feign.authority.vo.RoleMenuVo;
import com.rngay.service_authority.service.RoleMenuService;
import com.rngay.service_authority.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    public Result<RoleMenuVo> listMenu(Long roleId) {
        if (roleId == null) {
            return Result.failMsg("加载菜单失败");
        }
        RoleMenuVo menuVo = new RoleMenuVo();
        List<RoleMenuDTO> roleMenu = roleMenuService.listMenu(roleId);
        if (roleMenu != null && !roleMenu.isEmpty()) {
            List<Long> menuId = new ArrayList<>();
            List<Long> checked = new ArrayList<>();
            roleMenu.forEach(key -> menuId.add(key.getMenuId()));
            roleMenu.forEach(key -> {
                if (key.getMenuType() == 1) {
                    menuId.add(key.getMenuId());
                }
                if (key.getMenuType() == 2) {
                    checked.add(key.getMenuId());
                }
            });
            menuVo.setRoleMenu(menuId);
            List<MenuDTO> menu = roleMenuService.listAuth(menuId);
            menu = menu.stream().filter(m -> m.getChildren() != null && !m.getChildren().isEmpty())
                    .collect(Collectors.toList());
            menuVo.setMenu(menu);
            menuVo.setChecked(checked);
        }
        return Result.success(menuVo);
    }

    @PostMapping(value = "insert")
    public Result<Boolean> save(@Valid @RequestBody UpdateRoleMenuQuery query) {
        return Result.success(roleMenuService.insert(query));
    }

    @PostMapping(value = "listAuth")
    public Result<List<MenuDTO>> listAuth(@Valid @RequestBody ArrayQuery arrayQuery) {
        List<MenuDTO> menu = roleMenuService.listAuth(arrayQuery.getIds());
        menu = menu.stream().filter(m -> m.getChildren() != null && !m.getChildren().isEmpty())
                .collect(Collectors.toList());
        return Result.success(menu);
    }

}
