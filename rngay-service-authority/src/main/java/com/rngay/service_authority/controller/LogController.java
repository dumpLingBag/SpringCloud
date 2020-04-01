package com.rngay.service_authority.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.query.LoginInfoPageQuery;
import com.rngay.feign.authority.LoginInfoDTO;
import com.rngay.feign.authority.OperationLogDTO;
import com.rngay.feign.authority.query.OperationLogPageQuery;
import com.rngay.service_authority.service.LoginInfoService;
import com.rngay.service_authority.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "log")
public class LogController {

    @Autowired
    private LoginInfoService loginInfoService;
    @Autowired
    private OperationLogService operationLogService;

    @GetMapping(value = "loginInfo")
    public Result<Page<LoginInfoDTO>> loginInfo(LoginInfoPageQuery loginInfoPageQuery) {
        Page<LoginInfoDTO> page = new Page<>(loginInfoPageQuery.getCurrentPage(), loginInfoPageQuery.getPageSize());
        return Result.success(loginInfoService.pageList(page, loginInfoPageQuery));
    }

    @GetMapping(value = "operation")
    public Result<Page<OperationLogDTO>> operation(OperationLogPageQuery logPageQuery) {
        Page<OperationLogDTO> page = new Page<>(logPageQuery.getCurrentPage(), logPageQuery.getPageSize());
        return Result.success(operationLogService.pageList(page, logPageQuery));
    }

}
