package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.platform.MenuIdListDTO;
import com.rngay.feign.user.dto.UAUserDTO;
import com.rngay.service_authority.model.UAMenu;
import com.rngay.service_authority.service.UAMenuService;
import com.rngay.service_authority.service.UASystemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "authorityMenu")
public class UAMenuController {

    @Resource
    private UAMenuService menuService;
    @Resource
    private UASystemService systemService;

    @RequestMapping(value = "save")
    public Result<?> save(@RequestBody UAMenu uaMenu){
        Integer menu = menuService.save(uaMenu);
        if (menu == null) {
            return Result.fail("保存失败");
        }
        return Result.success(menu);
    }

    @RequestMapping(value = "getAllMenu")
    public Result<?> getAllMenu(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> allMenu = menuService.getAllMenu();
        UAUserDTO currentUser = systemService.getCurrentUser(request);
        map.put("menu", allMenu);
        map.put("user", currentUser);
        return Result.success(map);
    }

    @RequestMapping(value = "delete")
    public Result<?> delete(@RequestBody MenuIdListDTO menuIdList) {
        if (menuIdList == null || menuIdList.getMenuIdList().isEmpty()) {
            return Result.fail("不存在要删除的菜单");
        }
        return Result.success(menuService.delete(menuIdList));
    }

}
