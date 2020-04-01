package com.rngay.service_authority.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rngay.feign.authority.OperationLogDTO;
import com.rngay.feign.authority.query.OperationLogPageQuery;
import com.rngay.service_authority.dao.OperationLogDao;
import com.rngay.service_authority.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogDao, OperationLogDTO> implements OperationLogService {

    @Autowired
    private OperationLogDao operationLogDao;

    @Override
    public Page<OperationLogDTO> pageList(Page<OperationLogDTO> page, OperationLogPageQuery operationLogPageQuery) {
        return operationLogDao.pageList(page, operationLogPageQuery);
    }
}
