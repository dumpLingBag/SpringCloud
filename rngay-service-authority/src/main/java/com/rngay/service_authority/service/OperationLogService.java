package com.rngay.service_authority.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rngay.feign.authority.OperationLogDTO;
import com.rngay.feign.authority.query.OperationLogPageQuery;

public interface OperationLogService extends IService<OperationLogDTO> {

    Page<OperationLogDTO> pageList(Page<OperationLogDTO> page, OperationLogPageQuery operationLogPageQuery);

}
