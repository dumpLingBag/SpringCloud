package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.authority.MenuDTO;
import com.rngay.feign.user.dto.UaIconDTO;
import com.rngay.feign.user.dto.UaUserDTO;
import com.rngay.feign.user.service.PFUserService;
import com.rngay.service_authority.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(value = "authoritySys")
public class SystemController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private PFUserService userService;

    @GetMapping(value = "loadForMenu")
    public Result<List<MenuDTO>> loadForMenu(HttpServletRequest request) {
        UaUserDTO currentUser = systemService.getCurrentUser(request);
        if (currentUser == null) {
            return Result.success();
        }

        List<MenuDTO> menuList = systemService.loadForMenu(currentUser);

        return Result.success(menuList);
    }

    @GetMapping(value = "loadIcon")
    public Result<List<UaIconDTO>> loadIcon() {
        return userService.loadIcon();
    }

}
