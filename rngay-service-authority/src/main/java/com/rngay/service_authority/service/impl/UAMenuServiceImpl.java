package com.rngay.service_authority.service.impl;

import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.service_authority.service.UAMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UAMenuServiceImpl implements UAMenuService {

    @Resource
    private Dao dao;

    @Override
    public Integer save(String name, Integer pid, Integer sort) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("pid", pid);
        map.put("sort", sort);
        map.put("keep_alive", 0);
        map.put("auth", 1);
        return dao.insert(map, "ua_menu");
    }

    @Override
    public List<Map<String, Object>> getAllMenu() {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> menus = dao.query("ua_menu", Cnd.where("pid", "=", 0));
        for (Map<String, Object> menu : menus) {
            Map<String, Object> menuChildren = new HashMap<>();
            menuChildren.put("id", menu.get("id"));
            menuChildren.put("name", menu.get("name"));
            menuChildren.put("pid", menu.get("pid"));
            menuChildren.put("sort", menu.get("sort"));
            menuChildren.put("url", menu.get("url"));
            menuChildren.put("icon", menu.get("icon"));
            menuChildren.put("path", menu.get("path"));
            menuChildren.put("component", menu.get("component"));
            Map<String, Object> map = new HashMap<>();
            map.put("keepAlive", menu.get("keep_alive"));
            map.put("auth", menu.get("auth"));
            map.put("title", menu.get("name"));
            menuChildren.put("meta", map);
            menuChildren.put("children", getChildren((Integer) menu.get("id")));
            list.add(menuChildren);
        }
        return list;
    }

    @Override
    public List<Map<String, Object>> getUserForMenu() {
        return null;
    }

    private List<Object> getChildren(Integer parentId){
        List<Object> list = new ArrayList<>();
        List<Map<String, Object>> children = dao.query("ua_menu", Cnd.where("pid", "=", parentId));
        for (Map<String, Object> menu : children) {
            if (parentId.equals(menu.get("pid"))) {
                list.add(children(menu));
            }
        }
        return list;
    }

    private Map<String, Object> children(Map<String, Object> menu){
        Map<String, Object> menuChildren = new HashMap<>();
        menuChildren.put("id", menu.get("id"));
        menuChildren.put("name", menu.get("name"));
        menuChildren.put("pid", menu.get("pid"));
        menuChildren.put("sort", menu.get("sort"));
        menuChildren.put("url", menu.get("url"));
        menuChildren.put("icon", menu.get("icon"));
        menuChildren.put("path", menu.get("path"));
        menuChildren.put("component", menu.get("component"));
        Map<String, Object> map = new HashMap<>();
        map.put("keepAlive", menu.get("keepAlive"));
        map.put("auth", menu.get("auth"));
        map.put("title", menu.get("title"));
        menuChildren.put("meta", map);
        menuChildren.put("children", getChildren((Integer) menu.get("id")));
        return menuChildren;
    }
}
