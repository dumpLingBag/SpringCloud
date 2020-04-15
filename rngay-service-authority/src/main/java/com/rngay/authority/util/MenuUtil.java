package com.rngay.authority.util;

import com.rngay.authority.constant.Constant;
import com.rngay.feign.authority.MenuDTO;
import com.rngay.feign.authority.vo.MetaVo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 菜单树结构工具类
 * @Author: pengcheng
 * @Date: 2020/4/15
 */
public class MenuUtil {

    public static List<MenuDTO> menuList(List<MenuDTO> menuList, String field) {
        List<MenuDTO> arrList = new ArrayList<>();
        for (MenuDTO menu : menuList) {
            if (menu.getPid() == null || menu.getPid() == 0) {
                arrList.add(menuDtoToMenu(menuList, menu, field));
            }
        }
        arrList.sort(Comparator.comparing(MenuDTO::getSort));
        return arrList;
    }

    public static List<MenuDTO> authList(List<MenuDTO> menuList, String field) {
        List<MenuDTO> arrList = new ArrayList<>();
        for (MenuDTO menu : menuList) {
            if (menu.getMenuType() != null && menu.getMenuType() == 1)  {
                arrList.add(menuDtoToMenu(menuList, menu, field));
            }
        }
        arrList.sort(Comparator.comparing(MenuDTO::getSort));
        return arrList;
    }

    private static List<MenuDTO> menuListChildren(List<MenuDTO> menuList, MenuDTO menu, String field) {
        List<MenuDTO> children = new ArrayList<>();
        for (MenuDTO pid : menuList) {
            if (pid.getPid().equals(menu.getId())) {
                children.add(menuDtoToMenu(menuList, pid, field));
            }
        }
        children.sort(Comparator.comparing(MenuDTO::getSort));
        return children;
    }

    private static MenuDTO menuDtoToMenu(List<MenuDTO> menuList, MenuDTO menu, String field) {
        MenuDTO menuDto = new MenuDTO();
        menuDto(menuDto, menu, field);
        List<MenuDTO> menuDTOs = menuListChildren(menuList, menu, field);
        if (!menuDTOs.isEmpty()) {
            menuDto.setChildren(menuDTOs);
        }
        return menuDto;
    }

    public static void menuDto(MenuDTO menuDto, MenuDTO menu, String field) {
        menuDto.setId(menu.getId());
        menuDto.setName(menu.getName());
        menuDto.setLabel(menu.getName());
        menuDto.setPid(menu.getPid());
        menuDto.setSort(menu.getSort());
        menuDto.setIcon(menu.getIcon());
        menuDto.setPath(menu.getPath());
        menuDto.setComponent(menu.getComponent());
        menuDto.setEnabled(menu.getEnabled());
        menuDto.setAuthority(menu.getAuthority());
        menuDto.setMenuType(menu.getMenuType());
        menuDto.setMenuUrl(menu.getMenuUrl());
        menuDto.setCreateTime(menu.getCreateTime());
        menuDto.setUpdateTime(menu.getUpdateTime());
        if (field.equals(Constant.MENU_TYPE_VO)) {
            MetaVo metaVo = new MetaVo();
            metaVo.setKeepAlive(menu.getKeepAlive());
            metaVo.setAuth(menu.getAuth());
            metaVo.setTitle(menu.getName());
            menuDto.setMeta(metaVo);
        }
    }

}
