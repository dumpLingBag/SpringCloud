package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.platform.MenuDTO;
import com.rngay.feign.user.dto.UAUserDTO;
import com.rngay.feign.user.service.PFUserService;
import com.rngay.service_authority.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "authoritySys", name = "系统管理")
public class SystemController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private PFUserService userService;

    @RequestMapping(value = "loadForMenu", method = RequestMethod.GET, name = "加载指定用户的菜单数据")
    public Result<?> loadForMenu(HttpServletRequest request) {
        UAUserDTO currentUser = systemService.getCurrentUser(request);
        if (currentUser == null) {
            return Result.success(null);
        }

        List<MenuDTO> menuList = systemService.loadForMenu(currentUser);
        currentUser.setMenuList(menuList);

        Set<String> urlSet = systemService.getUrlSet(currentUser);
        currentUser.setUrlSet(urlSet);

        systemService.updateCurrentUser(request, currentUser);

        return Result.success(menuList);
    }

    @RequestMapping(value = "loadIcon", method = RequestMethod.GET, name = "加载图标")
    public Result<?> loadIcon() {
        return userService.loadIcon();
    }

}
