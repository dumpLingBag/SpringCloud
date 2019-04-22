package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.platform.CommonUrlDTO;
import com.rngay.service_authority.service.UACommonService;
import com.rngay.service_authority.service.UAMenuUrlService;
import com.rngay.service_authority.service.UASystemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "authorityCommonUrl", name = "公共权限")
public class UACommonController {

    @Resource
    private UASystemService systemService;
    @Resource
    private UACommonService commonService;
    @Resource
    private UAMenuUrlService menuUrlService;

    @RequestMapping(value = "load", method = RequestMethod.GET, name = "加载权限")
    public Result<?> load(HttpServletRequest request) {
        Integer orgId = systemService.getCurrentOrgId(request);
        if (orgId != null && orgId.equals(0)) {
            return Result.success(menuUrlService.load());
        }
        return Result.success(null);
    }

    @RequestMapping(value = "loadOpen", method = RequestMethod.GET, name = "选中的权限")
    public Result<?> loadOpen(HttpServletRequest request) {
        Integer orgId = systemService.getCurrentOrgId(request);
        if (orgId != null && orgId.equals(0)) {
            return Result.success(commonService.loadOpen());
        }
        return Result.success(null);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST, name = "选中权限")
    public Result<?> update(@RequestBody CommonUrlDTO urlDTO) {
        if (urlDTO.getUrlId() == null || urlDTO.getUrlId().isEmpty()) {
            return Result.failMsg("公共权限修改失败");
        }
        return Result.success(commonService.update(urlDTO));
    }

}
