package com.rngay.service_authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.feign.authority.OperLogDTO;
import com.rngay.feign.authority.OperLogPageDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OperLogDao extends BaseMapper<OperLogDTO> {

    Page<OperLogDTO> pageList(Page<OperLogDTO> page, @Param("pageList") OperLogPageDTO operLogPageDTO);

}
