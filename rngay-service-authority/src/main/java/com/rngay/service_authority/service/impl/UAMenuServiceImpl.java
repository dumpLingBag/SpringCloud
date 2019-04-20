package com.rngay.service_authority.service.impl;

import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.common.jpa.dao.util.cri.Static;
import com.rngay.feign.platform.MenuIdListDTO;
import com.rngay.service_authority.model.UAMenu;
import com.rngay.service_authority.model.UAMenuUrl;
import com.rngay.service_authority.model.UARoleMenu;
import com.rngay.service_authority.service.UAMenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UAMenuServiceImpl implements UAMenuService {

    @Resource
    private Dao dao;

    @Transactional
    @Override
    public Integer save(UAMenu uaMenu) {
        UAMenu byId = dao.findById(UAMenu.class, uaMenu.getPid());
        byId.setComponent(null);
        dao.update(byId);
        return dao.insert(uaMenu);
    }

    @Override
    public Integer update(UAMenu uaMenu) {
        return dao.update(uaMenu);
    }

    @Override
    public List<Map<String, Object>> load() {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> menus = dao.query("ua_menu", Cnd.where("pid", "=", 0));
        for (Map<String, Object> menu : menus) {
            Map<String, Object> menuChildren = new HashMap<>();
            menuChildren.put("id", menu.get("id"));
            menuChildren.put("name", menu.get("name"));
            menuChildren.put("pid", menu.get("pid"));
            menuChildren.put("sort", menu.get("sort"));
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
    public List<UAMenu> loadByPid() {
        return dao.query(UAMenu.class, Cnd.where("pid","=",0).and(new Static("component IS NULL")));
    }

    @Transactional
    @Override
    public Integer delete(MenuIdListDTO menuIdList) {
        if (menuIdList.getMenuIdList().size() > 1) {
            dao.delete(UAMenuUrl.class, Cnd.where("menu_id","in", menuIdList.getMenuIdList()));
            dao.delete(UARoleMenu.class, Cnd.where("menu_id","in", menuIdList.getMenuIdList()));
            dao.delete(UAMenu.class, Cnd.where("id","in", menuIdList.getMenuIdList()));
            return menuIdList.getMenuIdList().size();
        }
        UAMenu menu = dao.findById(UAMenu.class, menuIdList.getMenuIdList().get(0));
        if (menu != null) {
            dao.delete(UAMenuUrl.class, Cnd.where("menu_id","=", menu.getId()));
            dao.delete(UARoleMenu.class, Cnd.where("menu_id","=", menu.getId()));
            dao.delete(UAMenu.class, menu.getId());
            if (!menu.getPid().equals(0)) {
                List<UAMenu> pid = dao.query(UAMenu.class, Cnd.where("pid", "=", menu.getPid()).and("sort",">", menu.getSort()));
                if (pid != null && !pid.isEmpty()) {
                    StringBuilder sort = new StringBuilder();
                    pid.forEach(kev -> sort.append(kev.getId()).append(","));
                    sort.deleteCharAt(sort.length() - 1);
                    dao.update("UPDATE ua_menu SET sort = sort - 1 WHERE id IN ("+sort.toString()+")");
                    return 1;
                }
                return 0;
            }
        }
        return 0;
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
