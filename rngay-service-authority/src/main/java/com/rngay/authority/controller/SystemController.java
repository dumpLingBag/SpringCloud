package com.rngay.authority.controller;

import com.rngay.authority.constant.Constant;
import com.rngay.common.util.CookieUtil;
import com.rngay.common.util.StringUtils;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.MenuAuthDTO;
import com.rngay.feign.authority.MenuDTO;
import com.rngay.feign.user.dto.UaIconDTO;
import com.rngay.feign.user.dto.UaUserDTO;
import com.rngay.feign.user.service.PFUserService;
import com.rngay.authority.service.MenuService;
import com.rngay.authority.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
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

        List<MenuDTO> menuList = systemService.listForMenu(currentUser, Constant.POWER);

        MenuAuthDTO menuAuthDTO = new MenuAuthDTO();
        menuAuthDTO.setMenuList(menuList);
        if (currentUser.getParentId() != null && currentUser.getParentId() == 0) {
            Set<String> hashSet = new HashSet<>();
            hashSet.add("*:*:*");
            menuAuthDTO.setAuthorities(hashSet);
        } else {
            List<String> urlList = menuService.listUrlByUser(currentUser.getId());
            Set<String> grantedAuth = new HashSet<>(urlList);
            menuAuthDTO.setAuthorities(grantedAuth);
        }

        return Result.success(menuAuthDTO);
    }

    @PutMapping(value = "lang")
    public Result<Boolean> lang(String lang, HttpServletResponse response) {
        if (StringUtils.isNotBlank(lang)) {
            CookieUtil.writeCookie(response, "lang", lang);
            return Result.success(true);
        }
        return Result.failMsg("缺少语言参数");
    }

    @GetMapping(value = "listIcon")
    public Result<List<UaIconDTO>> listIcon() {
        return userService.listIcon();
    }

}
