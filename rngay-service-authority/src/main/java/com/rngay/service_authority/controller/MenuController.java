package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.platform.MenuDTO;
import com.rngay.feign.platform.MenuIdListDTO;
import com.rngay.service_authority.service.MenuService;
import com.rngay.service_authority.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "authorityMenu", name = "菜单管理")
public class MenuController {

    @Autowired
    private MenuService menuService;
    @Autowired
    private SystemService systemService;

    @RequestMapping(value = "save", method = RequestMethod.POST, name = "保存菜单")
    public Result<?> save(@RequestBody MenuDTO uaMenu) {
        Integer menu = menuService.save(uaMenu);
        if (menu == null) {
            return Result.failMsg("保存失败");
        }
        return Result.success(menu);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST, name = "修改菜单")
    public Result<?> update(@RequestBody MenuDTO uaMenu) {
        Integer menu = menuService.update(uaMenu);
        if (menu == null) {
            return Result.failMsg("修改失败");
        }
        return Result.success(menu);
    }

    @RequestMapping(value = "load", method = RequestMethod.GET, name = "加载菜单")
    public Result<?> load(HttpServletRequest request) {
        Integer orgId = systemService.getCurrentOrgId(request);
        if (orgId != null && orgId.equals(0)) {
            return Result.success(menuService.load());
        }
        return Result.failMsg("菜单加载失败");
    }

    @RequestMapping(value = "loadByPid", method = RequestMethod.GET, name = "获取父级菜单")
    public Result<?> loadByPid(HttpServletRequest request) {
        Integer orgId = systemService.getCurrentOrgId(request);
        if (orgId != null && orgId.equals(0)) {
            return Result.success(menuService.loadByPid());
        }
        return Result.failMsg("菜单加载失败");
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST, name = "删除菜单")
    public Result<?> delete(@RequestBody MenuIdListDTO menuIdList) {
        if (menuIdList == null || menuIdList.getMenuIdList().isEmpty()) {
            return Result.failMsg("删除菜单失败");
        }
        return Result.success(menuService.delete(menuIdList));
    }

}
