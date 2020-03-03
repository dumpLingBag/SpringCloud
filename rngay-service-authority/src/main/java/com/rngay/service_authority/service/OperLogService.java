package com.rngay.service_authority.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rngay.feign.authority.OperLogDTO;
import com.rngay.feign.authority.OperLogPageDTO;

public interface OperLogService extends IService<OperLogDTO> {

    Page<OperLogDTO> pageList(Page<OperLogDTO> page, OperLogPageDTO operLogPageDTO);

}
