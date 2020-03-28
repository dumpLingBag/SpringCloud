package com.rngay.service_authority.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rngay.common.enums.FiledEnum;
import com.rngay.feign.authority.MenuDTO;
import com.rngay.feign.authority.RoleMenuDTO;
import com.rngay.feign.authority.query.UpdateRoleMenuQuery;
import com.rngay.service_authority.dao.RoleMenuDao;
import com.rngay.service_authority.service.MenuService;
import com.rngay.service_authority.service.RoleMenuService;
import com.rngay.service_authority.util.MenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuDao, RoleMenuDTO> implements RoleMenuService {

    @Autowired
    private RoleMenuDao roleMenuDao;
    @Autowired
    private MenuService menuService;

    @Override
    public List<MenuDTO> load(Long orgId) {
        return menuList(menuService.loadMenuByOrgId(orgId));
    }

    @Override
    public List<RoleMenuDTO> loadMenu(Long roleId) {
        return roleMenuDao.loadMenu(roleId);
    }

    @Override
    public Boolean save(UpdateRoleMenuQuery query) {
        List<RoleMenuDTO> list = new ArrayList<>();
        for (Long menuId : query.getMenuId()) {
            RoleMenuDTO roleMenuDTO = new RoleMenuDTO();
            roleMenuDTO.setMenuId(menuId);
            roleMenuDTO.setRoleId(query.getRoleId());
            roleMenuDTO.setDelFlag(query.getType());
            list.add(roleMenuDTO);
        }
        if (!list.isEmpty()) {
            if (query.getType() == 0) {
                roleMenuDao.updateBatch(list);
                return true;
            } else {
                return saveBatch(list);
            }

        }
        return false;
    }

    private List<MenuDTO> menuList(List<MenuDTO> menus) {
        return MenuUtil.menuList(menus, FiledEnum.MENU_TYPE_NOT_VO);
    }

}
