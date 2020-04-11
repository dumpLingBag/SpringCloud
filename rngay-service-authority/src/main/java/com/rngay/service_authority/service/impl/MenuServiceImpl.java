package com.rngay.service_authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rngay.common.enums.FiledEnum;
import com.rngay.feign.authority.*;
import com.rngay.service_authority.dao.*;
import com.rngay.service_authority.service.MenuService;
import com.rngay.service_authority.util.MenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class MenuServiceImpl extends ServiceImpl<MenuDao, MenuDTO> implements MenuService {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private RoleMenuDao roleMenuDao;
    @Autowired
    private OrgRoleDao orgRoleDao;
    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public Integer insertMenu(MenuDTO uaMenu) {
        // 如果添加的菜单是跳转菜单，则判断上级菜单是不是一级菜单
        if (uaMenu.getMenuType() != null && uaMenu.getMenuType() == 1) {
            MenuDTO menuDTO = menuDao.selectById(uaMenu.getPid());
            if (menuDTO != null) {
                if (menuDTO.getMenuType() == 1 && uaMenu.getMenuType() == 1) {
                    menuDTO.setMenuType(0);
                    menuDTO.setComponent("");
                    menuDTO.setPath("");
                    menuDao.updateById(menuDTO);
                }
            }
        }
        return menuDao.insert(uaMenu);
    }

    @Override
    public List<MenuDTO> list() {
        List<MenuDTO> list = new ArrayList<>();
        List<MenuDTO> menus = menuDao.selectList(new QueryWrapper<MenuDTO>().eq("pid", 0));
        if (!menus.isEmpty()) {
            for (MenuDTO menu : menus) {
                MenuDTO mu = new MenuDTO();
                children(mu, menu);
                list.add(mu);
            }
        }
        list.sort(Comparator.comparing(MenuDTO::getSort));
        return list;
    }

    @Override
    public List<MenuDTO> listByPid() {
        return menuDao.selectList(new QueryWrapper<MenuDTO>().eq("pid",0).isNull("component"));
    }

    @Override
    public Integer delete(MenuIdListDTO menuIdList) {
        if (menuIdList.getMenuIdList().size() > 1) {
            roleMenuDao.delete(new QueryWrapper<RoleMenuDTO>().in("menu_id", menuIdList.getMenuIdList()));
            menuDao.deleteBatchIds(menuIdList.getMenuIdList());
            return menuIdList.getMenuIdList().size();
        }
        MenuDTO menu = menuDao.selectById(menuIdList.getMenuIdList().get(0));
        if (menu != null) {
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
    public List<MenuDTO> listMenuByOrgId(Long orgId, FiledEnum filedEnum) {
        if (orgId != null && orgId > 0) {
            List<OrgRoleDTO> roles = orgRoleDao.selectList(new QueryWrapper<OrgRoleDTO>()
                    .eq("del_flag", 1).eq("org_id", orgId));
            if (roles == null || roles.isEmpty()) {
                return new ArrayList<>();
            }
            return menuDao.listMenuByOrgId(roles, filedEnum.getCode());
        }
        QueryWrapper<MenuDTO> wrapper = new QueryWrapper<>();
        wrapper.eq("enabled", "1").eq("del_flag", "1");
        if (filedEnum != null) {
            wrapper.ne("menu_type", filedEnum.getCode());
        }
        wrapper.orderByAsc("pid").orderByAsc("sort");
        return menuDao.selectList(wrapper);
    }

    @Override
    public List<MenuDTO> listMenuByUserId(Long orgId, Long userId, FiledEnum filedEnum) {
        List<UserRoleDTO> roleIds = userRoleDao.listRoleId(userId);
        if (roleIds.isEmpty()) return new ArrayList<>();
        if (orgId != null && orgId > 0) {
            List<OrgRoleDTO> orgRoles = orgRoleDao.selectList(new QueryWrapper<OrgRoleDTO>()
            .eq("del_flag", 1).eq("org_id", orgId));
            if (orgRoles == null || orgRoles.isEmpty()) return new ArrayList<>();
            return menuDao.listMenuByOrgUserId(orgRoles, roleIds, filedEnum.getCode());
        }
        return menuDao.listMenuByUserId(roleIds, filedEnum.getCode());
    }

    @Override
    public Integer updateInMenu(MenuInListDTO menuIdListDTO) {
        return menuDao.updateInMenu(menuIdListDTO);
    }

    @Override
    public List<String> listUrlByUser(Long userId) {
        return menuDao.listUrlByUser(userId);
    }

    @Override
    public List<MenuDTO> listAuth(List<Long> menuId) {
        return menuDao.listAuth(menuId);
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
        list.sort(Comparator.comparing(MenuDTO::getSort));
        return list;
    }

    private void children(MenuDTO mu,MenuDTO menu) {
        MenuUtil.menuDto(mu, menu, FiledEnum.MENU_TYPE_NOT_VO);
        List<MenuDTO> children = getChildren(menu.getId());
        if (!children.isEmpty()) {
            mu.setChildren(children);
        }
    }
}
