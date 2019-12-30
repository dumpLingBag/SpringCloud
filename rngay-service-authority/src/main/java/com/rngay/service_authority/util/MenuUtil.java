package com.rngay.service_authority.util;

import com.rngay.feign.authority.MenuDTO;
import com.rngay.feign.authority.vo.MetaVo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MenuUtil {

    private static Integer menuType = null;

    public static List<MenuDTO> menuList(List<MenuDTO> menuList, Integer type) {
        menuType = type;
        List<MenuDTO> arrList = new ArrayList<>();
        for (MenuDTO menu : menuList) {
            if (menu.getPid() == null || menu.getPid() == 0) {
                arrList.add(menuDtoToMenu(menuList, menu));
            }
        }
        arrList.sort(Comparator.comparing(MenuDTO::getSort));
        return arrList;
    }

    private static List<MenuDTO> menuListChildren(List<MenuDTO> menuList, MenuDTO menu) {
        List<MenuDTO> children = new ArrayList<>();
        for (MenuDTO pid : menuList) {
            if (pid.getPid().equals(menu.getId())) {
                children.add(menuDtoToMenu(menuList, pid));
            }
        }
        children.sort(Comparator.comparing(MenuDTO::getSort));
        return children;
    }

    private static MenuDTO menuDtoToMenu(List<MenuDTO> menuList, MenuDTO menu) {
        MenuDTO menuDto = new MenuDTO();
        menuDto(menuDto, menu);
        List<MenuDTO> menuDTOS = menuListChildren(menuList, menu);
        if (!menuDTOS.isEmpty()) {
            menuDto.setChildren(menuDTOS);
        }
        return menuDto;
    }

    public static void menuDto(MenuDTO menuDto, MenuDTO menu) {
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
        menuDto.setCreateTime(menu.getCreateTime());
        menuDto.setUpdateTime(menu.getUpdateTime());
        if (menuType == 1) {
            MetaVo metaVo = new MetaVo();
            metaVo.setKeepAlive(menu.getKeepAlive());
            metaVo.setAuth(menu.getAuth());
            metaVo.setTitle(menu.getName());
            menuDto.setMeta(metaVo);
        }
    }

}
