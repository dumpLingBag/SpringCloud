package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.service_authority.service.UAMenuService;
import com.rngay.service_authority.service.UASystemService;
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
    public Result<?> save(String name, Integer pid, Integer sort){
        pid = pid == null ? 0 : pid;
        sort = sort == null ? 0 : sort;
        Integer menu = menuService.save(name, pid, sort);
        if (menu == null) {
            return Result.fail("保存失败");
        }
        return Result.success(menu);
    }

    @RequestMapping(value = "getAllMenu")
    public Result<?> getAllMenu(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> allMenu = menuService.getAllMenu();
        Map<String, Object> currentUser = systemService.getCurrentUser(request);
        map.put("menu", allMenu);
        map.put("user", currentUser);
        return Result.success(map);
    }

    @RequestMapping(value = "loadForMenu")
    public Result<?> loadForMenu(){
        return Result.success();
    }

}
