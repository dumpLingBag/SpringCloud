package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.platform.UpdateUrlDTO;
import com.rngay.service_authority.service.UAMenuUrlService;
import com.rngay.service_authority.service.UASystemService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "authorityMenuUrl")
public class UAMenuUrlController {

    @Resource
    private UAMenuUrlService urlService;
    @Resource
    private UASystemService systemService;

    @RequestMapping(value = "load", method = RequestMethod.GET)
    public Result<?> load(HttpServletRequest request) {
        Integer orgId = systemService.getCurrentOrgId(request);
        if (orgId != null && orgId.equals(0)) {
            return Result.success(urlService.load());
        }
        return Result.success(null);
    }

    @RequestMapping(value = "loadUrl", method = RequestMethod.POST)
    public Result<?> loadUrl(@Valid @RequestBody UpdateUrlDTO updateUrlDTO) {
        if (updateUrlDTO != null) {
            return Result.success(urlService.loadUrl(updateUrlDTO.getMenuId()));
        }
        return Result.failMsg("缺少 {id} 参数");
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public Result<?> update(@Valid @RequestBody UpdateUrlDTO updateUrlDTO) {
        return Result.success(urlService.update(updateUrlDTO));
    }

}
