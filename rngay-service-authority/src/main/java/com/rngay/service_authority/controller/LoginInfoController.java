package com.rngay.service_authority.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.LoginInfoDTO;
import com.rngay.feign.authority.query.ArrayQuery;
import com.rngay.feign.authority.query.LoginInfoPageQuery;
import com.rngay.service_authority.service.LoginInfoService;
import com.rngay.service_authority.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "loginInfo")
public class LoginInfoController {

    @Autowired
    private LoginInfoService loginInfoService;
    @Autowired
    private SystemService systemService;

    @GetMapping(value = "page")
    public Result<Page<LoginInfoDTO>> page(LoginInfoPageQuery loginInfoPageQuery) {
        Page<LoginInfoDTO> page = new Page<>(loginInfoPageQuery.getCurrentPage(), loginInfoPageQuery.getPageSize());
        return Result.success(loginInfoService.pageList(page, loginInfoPageQuery));
    }

    @DeleteMapping(value = "delete")
    public Result<Integer> delete(@RequestBody ArrayQuery arrayQuery) {
        Long orgId = systemService.getCurrentOrgId();
        if (orgId != null) {
            return Result.success(loginInfoService.delete(arrayQuery.getIds(), orgId));
        }
        return Result.failMsg("删除登录日志失败");
    }

    @DeleteMapping(value = "clear")
    public Result<Integer> clear() {
        Long orgId = systemService.getCurrentOrgId();
        if (orgId != null) {
            return Result.success(loginInfoService.clear(orgId));
        }
        return Result.failMsg("清空登录日志失败");
    }

}
