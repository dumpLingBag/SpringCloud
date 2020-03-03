package com.rngay.service_authority.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rngay.feign.authority.OperLogDTO;
import com.rngay.feign.authority.OperLogPageDTO;
import com.rngay.service_authority.dao.OperLogDao;
import com.rngay.service_authority.service.OperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperLogServiceImpl extends ServiceImpl<OperLogDao, OperLogDTO> implements OperLogService {

    @Autowired
    private OperLogDao operLogDao;

    @Override
    public Page<OperLogDTO> pageList(Page<OperLogDTO> page, OperLogPageDTO operLogPageDTO) {
        return operLogDao.pageList(page, operLogPageDTO);
    }
}
