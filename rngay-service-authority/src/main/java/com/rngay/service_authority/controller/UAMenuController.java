package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.platform.MenuIdListDTO;
import com.rngay.service_authority.model.UAMenu;
import com.rngay.service_authority.service.UAMenuService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "authorityMenu")
public class UAMenuController {

    @Resource
    private UAMenuService menuService;

    @RequestMapping(value = "save")
    public Result<?> save(@RequestBody UAMenu uaMenu){
        Integer menu = menuService.save(uaMenu);
        if (menu == null) {
            return Result.failMsg("保存失败");
        }
        return Result.success(menu);
    }

    @RequestMapping(value = "update")
    public Result<?> update(@RequestBody UAMenu uaMenu) {
        Integer menu = menuService.update(uaMenu);
        if (menu == null) {
            return Result.failMsg("修改失败");
        }
        return Result.success(menu);
    }

    @RequestMapping(value = "getAllMenu")
    public Result<?> getAllMenu(HttpServletRequest request){
        return Result.success(menuService.getAllMenu());
    }

    @RequestMapping(value = "delete")
    public Result<?> delete(@RequestBody MenuIdListDTO menuIdList) {
        if (menuIdList == null || menuIdList.getMenuIdList().isEmpty()) {
            return Result.failMsg("不存在要删除的菜单");
        }
        return Result.success(menuService.delete(menuIdList));
    }

}
