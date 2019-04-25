package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.platform.UpdateUrlDTO;
import com.rngay.service_authority.service.UAMenuUrlService;
import com.rngay.service_authority.service.UASystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "authorityMenuUrl", name = "菜单权限")
public class UAMenuUrlController {

    @Autowired
    private UAMenuUrlService urlService;
    @Autowired
    private UASystemService systemService;

    @RequestMapping(value = "load", method = RequestMethod.GET, name = "加载所有url")
    public Result<?> load(HttpServletRequest request) {
        Integer orgId = systemService.getCurrentOrgId(request);
        if (orgId != null && orgId.equals(0)) {
            return Result.success(urlService.load());
        }
        return Result.success(null);
    }

    @RequestMapping(value = "loadUrl", method = RequestMethod.POST, name = "加载选中的url")
    public Result<?> loadUrl(@Valid @RequestBody UpdateUrlDTO updateUrlDTO) {
        urlService.update(updateUrlDTO);
        return Result.success(urlService.loadUrl(updateUrlDTO.getMenuId()));
    }

    @RequestMapping(value = "update", method = RequestMethod.POST, name = "修改选中url")
    public Result<?> update(@Valid @RequestBody UpdateUrlDTO updateUrlDTO) {
        return Result.success(urlService.update(updateUrlDTO));
    }

}
