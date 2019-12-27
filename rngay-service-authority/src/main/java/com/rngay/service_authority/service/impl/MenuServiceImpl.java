package com.rngay.service_authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rngay.feign.authority.*;
import com.rngay.feign.authority.vo.MetaVo;
import com.rngay.service_authority.dao.*;
import com.rngay.service_authority.service.MenuService;
import com.rngay.service_authority.util.MenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private MenuUrlDao menuUrlDao;
    @Autowired
    private RoleMenuDao roleMenuDao;
    @Autowired
    private OrgRoleDao orgRoleDao;
    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public Integer save(MenuDTO uaMenu) {
        // 如果添加的菜单是跳转菜单，则判断上级菜单是不是一级菜单
        if (uaMenu.getComponent() != null && !uaMenu.getComponent().equals("")) {
            MenuDTO menu = menuDao.selectById(uaMenu.getPid());
            if (menu != null) {
                if (menu.getComponent() != null) {
                    if (menu.getComponent() != null) {
                        menu.setPath("0");
                        menu.setComponent("");
                        menu.setIcon("iconfont icon-xingzhuang-tuoyuanxing");
                        menuDao.updateById(menu);
                    }
                }
            }
        }
        return menuDao.insert(uaMenu);
    }

    @Override
    public Integer update(MenuDTO uaMenu) {
        return menuDao.updateById(uaMenu);
    }

    @Override
    public List<MenuDTO> load() {
        List<MenuDTO> list = new ArrayList<>();
        List<MenuDTO> menus = menuDao.selectList(new QueryWrapper<MenuDTO>().eq("pid", 0));
        if (!menus.isEmpty()) {
            for (MenuDTO menu : menus) {
                MenuDTO mu = new MenuDTO();
                children(mu, menu);
                list.add(mu);
            }
        }
        return list;
    }

    @Override
    public List<MenuDTO> loadByPid() {
        return menuDao.selectList(new QueryWrapper<MenuDTO>().eq("pid",0).isNull("component"));
    }

    @Override
    public Integer delete(MenuIdListDTO menuIdList) {
        if (menuIdList.getMenuIdList().size() > 1) {
            menuUrlDao.delete(new QueryWrapper<MenuUrlDTO>().in("menu_id", menuIdList.getMenuIdList()));
            roleMenuDao.delete(new QueryWrapper<RoleMenuDTO>().in("menu_id", menuIdList.getMenuIdList()));
            menuDao.deleteBatchIds(menuIdList.getMenuIdList());
            return menuIdList.getMenuIdList().size();
        }
        MenuDTO menu = menuDao.selectById(menuIdList.getMenuIdList().get(0));
        if (menu != null) {
            menuUrlDao.delete(new QueryWrapper<MenuUrlDTO>().eq("menu_id", menu.getId()));
            roleMenuDao.delete(new QueryWrapper<RoleMenuDTO>().eq("menu_id", menu.getId()));
            menuDao.deleteById(menu.getId());
            if (menu.getPid() != 0) {
                List<MenuDTO> pid = menuDao.selectList(new QueryWrapper<MenuDTO>().eq("pid", menu.getPid()).gt("sort", menu.getSort()));
                if (pid != null && !pid.isEmpty()) {
                    return menuDao.updateSort(pid);
                }
                return 0;
            }
        }
        return 0;
    }

    @Override
    public List<MenuDTO> loadMenuByOrgId(Integer orgId) {
        if (orgId != null && orgId > 0) {
            List<OrgRoleDTO> roles = orgRoleDao.selectList(new QueryWrapper<OrgRoleDTO>()
                    .eq("checked", 1).eq("org_id", orgId));
            if (roles == null || roles.isEmpty()) {
                return new ArrayList<>();
            }
            return menuDao.loadMenuByOrgId(roles);
        }
        return menuDao.selectList(new QueryWrapper<MenuDTO>().orderByAsc("pid").orderByAsc("sort"));
    }

    @Override
    public List<MenuDTO> loadMenuByUserId(Integer orgId, Long userId) {
        List<UserRoleDTO> roleIds = userRoleDao.getRoleId(userId);
        if (roleIds.isEmpty()) return new ArrayList<>();
        if (orgId != null && orgId > 0) {
            List<OrgRoleDTO> orgRoles = orgRoleDao.selectList(new QueryWrapper<OrgRoleDTO>()
            .eq("checked", 1).eq("org_id", orgId));
            if (orgRoles == null || orgRoles.isEmpty()) return new ArrayList<>();
            return menuDao.loadMenuByOrgUserId(orgRoles, roleIds);
        }
        return menuDao.loadMenuByUserId(roleIds);
    }

    private List<MenuDTO> getChildren(Long parentId) {
        List<MenuDTO> list = new ArrayList<>();
        List<MenuDTO> children = menuDao.selectList(new QueryWrapper<MenuDTO>().eq("pid", parentId));
        if (!children.isEmpty()) {
            for (MenuDTO menu : children) {
                if (parentId.equals(menu.getPid())) {
                    MenuDTO mu = new MenuDTO();
                    children(mu, menu);
                    list.add(mu);
                }
            }
        }
        return list;
    }

    private void children(MenuDTO mu,MenuDTO menu) {
        MenuUtil.menuDto(mu, menu);
        List<MenuDTO> children = getChildren(menu.getId());
        if (!children.isEmpty()) {
            mu.setChildren(children);
        }
    }
}
