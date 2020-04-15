package com.rngay.authority.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.OperationLogDTO;
import com.rngay.feign.authority.query.ArrayQuery;
import com.rngay.feign.authority.query.OperationLogPageQuery;
import com.rngay.authority.service.OperationLogService;
import com.rngay.authority.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "operation")
public class OperationLogController {

    @Autowired
    private OperationLogService operationLogService;
    @Autowired
    private SystemService systemService;

    @GetMapping(value = "page")
    public Result<Page<OperationLogDTO>> page(OperationLogPageQuery logPageQuery) {
        Page<OperationLogDTO> page = new Page<>(logPageQuery.getCurrentPage(), logPageQuery.getPageSize());
        return Result.success(operationLogService.pageOperationLog(page, logPageQuery));
    }

    @DeleteMapping(value = "delete")
    public Result<Integer> delete(@RequestBody ArrayQuery arrayQuery) {
        Long orgId = systemService.getCurrentOrgId();
        if (orgId != null) {
            return Result.success(operationLogService.delete(arrayQuery.getIds(), orgId));
        }
        return Result.failMsg("删除操作日志失败");
    }

    @DeleteMapping(value = "clear")
    public Result<Integer> clear() {
        Long orgId = systemService.getCurrentOrgId();
        if (orgId != null) {
            return Result.success(operationLogService.clear(orgId));
        }
        return Result.failMsg("清空操作日志失败");
    }

}
