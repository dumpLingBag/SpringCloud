package com.rngay.service_authority.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.LogPageDTO;
import com.rngay.feign.authority.LoginInfoDTO;
import com.rngay.feign.authority.OperLogDTO;
import com.rngay.feign.authority.OperLogPageDTO;
import com.rngay.service_authority.service.LoginInfoService;
import com.rngay.service_authority.service.OperLogService;
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
    private OperLogService operLogService;

    @GetMapping(value = "loginInfoIndex")
    public Result<Page<LoginInfoDTO>> loginInfoIndex(LogPageDTO logPageDTO) {
        Page<LoginInfoDTO> page = new Page<>(logPageDTO.getCurrentPage(), logPageDTO.getPageSize());
        return Result.success(loginInfoService.pageList(page, logPageDTO));
    }

    @GetMapping(value = "operLogIndex")
    public Result<Page<OperLogDTO>> operLogIndex(OperLogPageDTO logPageDTO) {
        Page<OperLogDTO> page = new Page<>(logPageDTO.getCurrentPage(), logPageDTO.getPageSize());
        return Result.success(operLogService.pageList(page, logPageDTO));
    }

}
