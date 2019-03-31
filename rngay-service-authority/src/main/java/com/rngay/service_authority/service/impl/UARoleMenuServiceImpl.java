package com.rngay.service_authority.service.impl;

import com.rngay.common.jpa.dao.Dao;
import com.rngay.service_authority.dao.UARoleMenuDao;
import com.rngay.service_authority.service.UARoleMenuService;
import com.rngay.service_authority.util.SortUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UARoleMenuServiceImpl implements UARoleMenuService {

    @Resource
    private Dao dao;
    @Resource
    private UARoleMenuDao roleMenuDao;

    @Override
    public List<Map<String, Object>> load(Integer orgId) {
        return menuList(roleMenuDao.loadMenuByOrgId(orgId));
    }

    @Override
    public List<Map<String, Object>> loadMenu(Integer roleId) {
        if (roleId == null) {
            return null;
        }
        return roleMenuDao.loadMenuByRoleId(roleId);
    }

    private List<Map<String, Object>> menuList(List<Map<String, Object>> menus) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Map<String, Object> menu : menus) {
            if (menu.get("pid") == null || menu.get("pid").equals(0)) {
                mapList.add(mapToMenu(menus, menu));
            }
        }
        return SortUtil.mapSort(mapList);
    }

    private List<Map<String, Object>> menuListChildren(List<Map<String, Object>> menus, Map<String, Object> menu) {
        List<Map<String, Object>> children = new ArrayList<>();
        for (Map<String, Object> pid : menus) {
            if (pid.get("pid").equals(menu.get("id"))) {
                children.add(mapToMenu(menus, pid));
            }
        }
        return SortUtil.mapSort(children);
    }

    private Map<String, Object> mapToMenu(List<Map<String, Object>> menus, Map<String, Object> menu) {
        Map<String, Object> menuMap = new HashMap<>();
        menuMap.put("id", menu.get("id"));
        menuMap.put("name", menu.get("name"));
        menuMap.put("sort", menu.get("sort"));
        menuMap.put("pid", menu.get("pid"));
        menuMap.put("children", menuListChildren(menus, menu));
        return menuMap;
    }

}
