package com.rngay.service_authority.controller;

import com.rngay.common.enums.ResultCodeEnum;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.CommonUrlDTO;
import com.rngay.feign.authority.UrlDTO;
import com.rngay.service_authority.service.CommonService;
import com.rngay.service_authority.service.MenuUrlService;
import com.rngay.service_authority.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "commonUrl", name = "公共权限")
public class CommonController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private MenuUrlService menuUrlService;

    @GetMapping(value = "load")
    public Result<List<UrlDTO>> load() {
        Long orgId = systemService.getCurrentOrgId();
        if (orgId != null && orgId == 0) {
            return Result.success(menuUrlService.load());
        }
        return Result.success();
    }

    @GetMapping(value = "loadOpen")
    public Result<List<UrlDTO>> loadOpen() {
        Long orgId = systemService.getCurrentOrgId();
        if (orgId != null && orgId == 0) {
            return Result.success(commonService.loadOpen());
        }
        return Result.success();
    }

    @PutMapping(value = "update")
    public Result<Integer> update(@RequestBody CommonUrlDTO urlDTO) {
        if (urlDTO.getUrlId() == null || urlDTO.getUrlId().isEmpty()) {
            return Result.fail(ResultCodeEnum.COMMON_AUTHORITY_FAIL);
        }
        return Result.success(commonService.update(urlDTO));
    }

}
