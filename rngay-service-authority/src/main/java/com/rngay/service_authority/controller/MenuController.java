package com.rngay.service_authority.controller;

import com.rngay.common.aspect.annotation.RepeatSubmit;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.MenuDTO;
import com.rngay.feign.authority.MenuIdListDTO;
import com.rngay.feign.authority.MenuInListDTO;
import com.rngay.service_authority.service.MenuService;
import com.rngay.service_authority.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "authorityMenu", name = "菜单管理")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private SystemService systemService;

    @RepeatSubmit
    @PostMapping(value = "save")
    public Result<Integer> save(@RequestBody MenuDTO uaMenu) {
        Integer menu = menuService.saveMenu(uaMenu);
        if (menu == null || menu == 0) {
            return Result.failMsg("保存失败");
        }
        return Result.success(menu);
    }

    @PutMapping(value = "update")
    public Result<Integer> update(@RequestBody MenuDTO uaMenu) {
        boolean menu = menuService.updateById(uaMenu);
        if (!menu) {
            return Result.failMsg("修改失败");
        }
        return Result.success();
    }

    @GetMapping(value = "load")
    public Result<List<MenuDTO>> load(HttpServletRequest request) {
        Long orgId = systemService.getCurrentOrgId(request);
        if (orgId != null && orgId == 0) {
            return Result.success(menuService.load());
        }
        return Result.failMsg("菜单加载失败");
    }

    @GetMapping(value = "loadByPid")
    public Result<List<MenuDTO>> loadByPid(HttpServletRequest request) {
        Long orgId = systemService.getCurrentOrgId(request);
        if (orgId != null && orgId == 0) {
            return Result.success(menuService.loadByPid());
        }
        return Result.failMsg("菜单加载失败");
    }

    @DeleteMapping(value = "delete")
    public Result<Integer> delete(@RequestBody MenuIdListDTO menuIdList) {
        if (menuIdList == null || menuIdList.getMenuIdList().isEmpty()) {
            return Result.failMsg("删除菜单失败");
        }
        return Result.success(menuService.delete(menuIdList));
    }

    @PutMapping(value = "updateInList")
    public Result<Integer> update(@RequestBody MenuInListDTO menuIdList) {
        if (menuIdList == null || menuIdList.getMenuIdList().isEmpty()) {
            return Result.failMsg("更改状态失败");
        }
        return Result.success(menuService.updateInList(menuIdList));
    }

}
