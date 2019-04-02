package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.platform.MenuIdListDTO;
import com.rngay.service_authority.model.UAMenu;
import com.rngay.service_authority.service.UAMenuService;
import com.rngay.service_authority.service.UASystemService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "authorityMenu")
public class UAMenuController {

    @Resource
    private UAMenuService menuService;
    @Resource
    private UASystemService systemService;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Result<?> save(@RequestBody UAMenu uaMenu){
        Integer menu = menuService.save(uaMenu);
        if (menu == null) {
            return Result.failMsg("保存失败");
        }
        return Result.success(menu);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result<?> update(@RequestBody UAMenu uaMenu) {
        Integer menu = menuService.update(uaMenu);
        if (menu == null) {
            return Result.failMsg("修改失败");
        }
        return Result.success(menu);
    }

    @RequestMapping(value = "load", method = RequestMethod.GET)
    public Result<?> getAllMenu(HttpServletRequest request){
        Integer orgId = systemService.getCurrentOrgId(request);
        if (orgId != null && orgId.equals(0)) {
            return Result.success(menuService.load());
        }
        return Result.failMsg("菜单加载失败");
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Result<?> delete(@RequestBody MenuIdListDTO menuIdList) {
        if (menuIdList == null || menuIdList.getMenuIdList().isEmpty()) {
            return Result.failMsg("删除菜单失败");
        }
        return Result.success(menuService.delete(menuIdList));
    }

}
