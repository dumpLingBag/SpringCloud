package com.rngay.authority.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rngay.feign.authority.OperationLogDTO;
import com.rngay.feign.authority.query.OperationLogPageQuery;
import com.rngay.authority.dao.OperationLogDao;
import com.rngay.authority.service.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogDao, OperationLogDTO> implements OperationLogService {

    @Autowired
    private OperationLogDao operationLogDao;

    @Override
    public Page<OperationLogDTO> pageOperationLog(Page<OperationLogDTO> page, OperationLogPageQuery operationLogPageQuery) {
        return operationLogDao.pageOperationLog(page, operationLogPageQuery);
    }

    @Override
    public int delete(List<Long> arrayQuery, Long orgId) {
        return operationLogDao.deleteOperationLog(arrayQuery, orgId);
    }

    @Override
    public int clear(Long orgId) {
        return operationLogDao.clear(orgId);
    }
}
