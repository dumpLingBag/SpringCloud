package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.user.service.PFUserService;
import com.rngay.service_authority.service.UASystemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "authoritySys")
public class UASystemController {

    @Resource
    private UASystemService systemService;
    @Resource
    private PFUserService userService;

    @RequestMapping(value = "getForMenu")
    public Result<?> loadForMenu(HttpServletRequest request){
        Map<String, Object> currentUser = systemService.getCurrentUser(request);
        if (currentUser == null || currentUser.isEmpty()){
            return Result.fail("加载菜单失败");
        }

        return Result.success();
    }

    @RequestMapping(value = "loadIcon")
    public Result<?> loadIcon(){
        return userService.loadIcon();
    }

}
