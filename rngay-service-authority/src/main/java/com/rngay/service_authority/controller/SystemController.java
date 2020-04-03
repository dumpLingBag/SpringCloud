package com.rngay.service_authority.controller;

import com.rngay.common.enums.FiledEnum;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.MenuAuthDTO;
import com.rngay.feign.authority.MenuDTO;
import com.rngay.feign.user.dto.UaIconDTO;
import com.rngay.feign.user.dto.UaUserDTO;
import com.rngay.feign.user.service.PFUserService;
import com.rngay.service_authority.service.MenuService;
import com.rngay.service_authority.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping(value = "system")
public class SystemController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private PFUserService userService;
    @Autowired
    private MenuService menuService;

    @GetMapping(value = "listForMenu")
    public Result<MenuAuthDTO> listForMenu() {
        UaUserDTO currentUser = systemService.getCurrentUser();
        if (currentUser == null) {
            return Result.success();
        }

        List<MenuDTO> menuList = systemService.listForMenu(currentUser, FiledEnum.POWER);

        MenuAuthDTO menuAuthDTO = new MenuAuthDTO();
        menuAuthDTO.setMenuList(menuList);
        if (currentUser.getParentId() != null && currentUser.getParentId() == 0) {
            Set<String> hashSet = new HashSet<>();
            hashSet.add("*:*:*");
            menuAuthDTO.setAuthorities(hashSet);
        } else {
            List<String> urlList = menuService.loadUrlByUser(currentUser.getId());
            Set<String> grantedAuth = new HashSet<>(urlList);
            menuAuthDTO.setAuthorities(grantedAuth);
        }

        return Result.success(menuAuthDTO);
    }

    @GetMapping(value = "listIcon")
    public Result<List<UaIconDTO>> listIcon() {
        return userService.listIcon();
    }

}
