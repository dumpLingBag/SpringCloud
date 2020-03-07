package com.rngay.service_authority.controller;

import com.rngay.common.enums.ResultCodeEnum;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.CommonUrlDTO;
import com.rngay.feign.authority.UrlDTO;
import com.rngay.service_authority.service.CommonService;
import com.rngay.service_authority.service.MenuUrlService;
import com.rngay.service_authority.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value = "authorityCommonUrl", name = "公共权限")
public class CommonController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private MenuUrlService menuUrlService;

    @RequestMapping(value = "load", method = RequestMethod.GET, name = "加载权限")
    public Result<List<UrlDTO>> load(HttpServletRequest request) {
        Long orgId = systemService.getCurrentOrgId(request);
        if (orgId != null && orgId == 0) {
            return Result.success(menuUrlService.load());
        }
        return Result.success();
    }

    @RequestMapping(value = "loadOpen", method = RequestMethod.GET, name = "选中的权限")
    public Result<List<UrlDTO>> loadOpen(HttpServletRequest request) {
        Long orgId = systemService.getCurrentOrgId(request);
        if (orgId != null && orgId == 0) {
            return Result.success(commonService.loadOpen());
        }
        return Result.success();
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT, name = "选中权限")
    public Result<Integer> update(@RequestBody CommonUrlDTO urlDTO) {
        if (urlDTO.getUrlId() == null || urlDTO.getUrlId().isEmpty()) {
            return Result.fail(ResultCodeEnum.COMMON_AUTHORITY_FAIL);
        }
        return Result.success(commonService.update(urlDTO));
    }

}
