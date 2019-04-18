package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.user.dto.UAUserDTO;
import com.rngay.feign.user.service.PFUserService;
import com.rngay.service_authority.service.UASystemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping(value = "authoritySys")
public class UASystemController {

    @Resource
    private UASystemService systemService;
    @Resource
    private PFUserService userService;

    @RequestMapping(value = "loadForMenu", method = RequestMethod.GET)
    public Result<?> loadForMenu(HttpServletRequest request){
        UAUserDTO currentUser = systemService.getCurrentUser(request);
        if (currentUser == null){
            return Result.success(null);
        }

        List<Map<String, Object>> menuList = systemService.loadForMenu(currentUser);
        currentUser.setMenuList(menuList);

        Set<String> urlSet = systemService.getUrlSet(currentUser);
        currentUser.setUrlSet(urlSet);

        systemService.updateCurrentUser(request, currentUser);

        return Result.success(menuList);
    }

    @RequestMapping(value = "loadIcon", method = RequestMethod.GET)
    public Result<?> loadIcon(){
        return userService.loadIcon();
    }

}
