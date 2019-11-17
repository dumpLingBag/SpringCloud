package com.rngay.service_authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rngay.feign.platform.MenuDTO;
import com.rngay.feign.platform.RoleMenuDTO;
import com.rngay.feign.platform.UpdateRoleMenuDTO;
import com.rngay.service_authority.dao.RoleMenuDao;
import com.rngay.service_authority.service.MenuService;
import com.rngay.service_authority.service.RoleMenuService;
import com.rngay.service_authority.util.SortUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuDao, RoleMenuDTO> implements RoleMenuService {

    @Autowired
    private RoleMenuDao roleMenuDao;
    @Autowired
    private MenuService menuService;

    @Override
    public List<MenuDTO> load(Integer orgId) {
        return menuList(menuService.loadMenuByOrgId(orgId));
    }

    @Override
    public List<RoleMenuDTO> loadMenu(Integer roleId) {
        return roleMenuDao.selectList(new QueryWrapper<RoleMenuDTO>().eq("checked", 1).eq("role_id", roleId));
    }

    @Override
    public Integer update(UpdateRoleMenuDTO roleMenu) {
        if (roleMenu.getRoleId() != null && !roleMenu.getRoleMenu().isEmpty()) {
            int i = 0;
            List<RoleMenuDTO> list = new ArrayList<>();
            for (RoleMenuDTO menuDTO : roleMenu.getRoleMenu()) {
                int roleId = roleMenuDao.update(menuDTO, new QueryWrapper<RoleMenuDTO>()
                        .eq("role_id", roleMenu.getRoleId())
                        .eq("menu_id", menuDTO.getMenuId()));
                if (roleId <= 0) {
                    menuDTO.setRoleId(roleMenu.getRoleId());
                    list.add(menuDTO);
                } else {
                    i += roleId;
                }
            }
            if (!list.isEmpty()) {
                saveBatch(list);
            }
            return i + list.size();
        }
        return 0;
    }

    private List<MenuDTO> menuList(List<MenuDTO> menus) {
        List<MenuDTO> menuList = new ArrayList<>();
        for (MenuDTO menu : menus) {
            if (menu.getPid() == null || menu.getPid() == 0) {
                menuList.add(arrToMenu(menus, menu));
            }
        }
        menuList.sort(Comparator.comparing(MenuDTO::getPid));
        return menuList;
    }

    private List<MenuDTO> menuListChildren(List<MenuDTO> menus, MenuDTO menu) {
        List<MenuDTO> children = new ArrayList<>();
        for (MenuDTO pid : menus) {
            if (pid.getPid().equals(menu.getId())) {
                children.add(arrToMenu(menus, pid));
            }
        }
        children.sort(Comparator.comparing(MenuDTO::getPid));
        return children;
    }

    private MenuDTO arrToMenu(List<MenuDTO> menus, MenuDTO menu) {
        MenuDTO menuArr = new MenuDTO();
        menuArr.setId(menu.getId());
        menuArr.setName(menu.getName());
        menuArr.setSort(menu.getSort());
        menuArr.setPid(menu.getPid());
        menuArr.setChildren(menuListChildren(menus, menu));
        return menuArr;
    }
}
