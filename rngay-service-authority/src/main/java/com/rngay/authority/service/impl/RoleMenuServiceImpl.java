package com.rngay.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rngay.authority.constant.Constant;
import com.rngay.feign.authority.MenuDTO;
import com.rngay.feign.authority.RoleMenuDTO;
import com.rngay.feign.authority.query.UpdateRoleMenuQuery;
import com.rngay.authority.dao.RoleMenuDao;
import com.rngay.authority.service.MenuService;
import com.rngay.authority.service.RoleMenuService;
import com.rngay.authority.util.MenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = Exception.class)
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuDao, RoleMenuDTO> implements RoleMenuService {

    @Autowired
    private RoleMenuDao roleMenuDao;
    @Autowired
    private MenuService menuService;

    @Override
    public List<MenuDTO> list(Long orgId, Integer filed) {
        return menuList(menuService.listMenuByOrgId(orgId, filed));
    }

    @Override
    public List<RoleMenuDTO> listMenu(Long roleId) {
        return roleMenuDao.listMenu(roleId);
    }

    @Override
    public Boolean insert(UpdateRoleMenuQuery query) {
        roleMenuDao.delete(new QueryWrapper<RoleMenuDTO>().eq("role_id", query.getRoleId()));
        List<RoleMenuDTO> list = new ArrayList<>();
        for (Long menuId : query.getMenuId()) {
            RoleMenuDTO roleMenuDTO = new RoleMenuDTO();
            roleMenuDTO.setMenuId(menuId);
            roleMenuDTO.setRoleId(query.getRoleId());
            list.add(roleMenuDTO);
        }
        return saveBatch(list);
    }

    @Override
    public List<RoleMenuDTO> listCheckMenu(Long roleId) {
        QueryWrapper<RoleMenuDTO> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        return roleMenuDao.selectList(wrapper);
    }

    private List<MenuDTO> menuList(List<MenuDTO> menus) {
        return MenuUtil.menuList(menus, Constant.MENU_TYPE_NOT_VO);
    }

}
