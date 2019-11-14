package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.platform.MenuUrlDTO;
import com.rngay.feign.platform.UpdateUrlDTO;
import com.rngay.service_authority.service.MenuUrlService;
import com.rngay.service_authority.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "authorityMenuUrl", name = "菜单权限")
public class MenuUrlController {

    @Autowired
    private MenuUrlService urlService;
    @Autowired
    private SystemService systemService;

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
        Integer update = urlService.update(updateUrlDTO);
        List<MenuUrlDTO> menuUrls = urlService.loadUrl(updateUrlDTO.getMenuId());
        Map<String, Object> map = new HashMap<>();
        map.put("update", update);
        map.put("menuUrls", menuUrls);
        return Result.success(map);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST, name = "修改选中url")
    public Result<?> update(@Valid @RequestBody UpdateUrlDTO updateUrlDTO) {
        return Result.success(urlService.update(updateUrlDTO));
    }

}
