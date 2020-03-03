package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.authority.MenuUrlDTO;
import com.rngay.feign.authority.UpdateUrlDTO;
import com.rngay.feign.authority.UrlDTO;
import com.rngay.service_authority.service.MenuUrlService;
import com.rngay.service_authority.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "authorityMenuUrl", name = "菜单权限")
public class MenuUrlController {

    @Autowired
    private MenuUrlService urlService;
    @Autowired
    private SystemService systemService;

    @RequestMapping(value = "load", method = RequestMethod.GET, name = "加载所有url")
    public Result<List<UrlDTO>> load(HttpServletRequest request) {
        Long orgId = systemService.getCurrentOrgId(request);
        if (orgId != null && orgId == 0) {
            return Result.success(urlService.load());
        }
        return Result.success(null);
    }

    @RequestMapping(value = "loadUrl", method = RequestMethod.POST, name = "加载选中的url")
    public Result<List<MenuUrlDTO>> loadUrl(@Valid @RequestBody UpdateUrlDTO updateUrlDTO) {
        List<MenuUrlDTO> menuUrls = urlService.loadUrl(updateUrlDTO.getMenuId());
        return Result.success(menuUrls);
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT, name = "修改选中url")
    public Result<Integer> update(@Valid @RequestBody UpdateUrlDTO updateUrlDTO) {
        return Result.success(urlService.update(updateUrlDTO));
    }

}
