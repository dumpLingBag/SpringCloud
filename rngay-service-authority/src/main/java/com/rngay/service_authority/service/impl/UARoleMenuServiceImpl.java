package com.rngay.service_authority.service.impl;

import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.feign.platform.RoleMenuDTO;
import com.rngay.feign.platform.UpdateRoleMenuDTO;
import com.rngay.service_authority.dao.UARoleMenuDao;
import com.rngay.service_authority.model.UARoleMenu;
import com.rngay.service_authority.service.UARoleMenuService;
import com.rngay.service_authority.util.SortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UARoleMenuServiceImpl implements UARoleMenuService {

    @Autowired
    private Dao dao;
    @Autowired
    private UARoleMenuDao roleMenuDao;

    @Override
    public List<Map<String, Object>> load(Integer orgId) {
        return menuList(roleMenuDao.loadMenuByOrgId(orgId));
    }

    @Override
    public List<UARoleMenu> loadMenu(Integer roleId) {
        return dao.query(UARoleMenu.class, Cnd.where("checked","=",1).and("role_id","=", roleId));
    }

    @Transactional
    @Override
    public Integer update(UpdateRoleMenuDTO roleMenu) {
        if (roleMenu.getRoleId() != null && !roleMenu.getRoleMenu().isEmpty()) {
            int i = 0;
            List<RoleMenuDTO> list = new ArrayList<>();
            for (RoleMenuDTO menu : roleMenu.getRoleMenu()) {
                int update = dao.update(menu, Cnd.where("role_id", "=", roleMenu.getRoleId()).and("menu_id", "=", menu.getMenuId()));
                if (update <= 0) {
                    menu.setRoleId(roleMenu.getRoleId());
                    list.add(menu);
                } else {
                    i += update;
                }
            }
            if (!list.isEmpty()) {
                int[] ints = dao.batchInsert(list);
                i = i + ints.length;
            }
            return i;
        }
        return 0;
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
