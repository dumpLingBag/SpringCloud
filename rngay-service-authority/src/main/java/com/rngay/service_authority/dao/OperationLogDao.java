package com.rngay.service_authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.feign.authority.OperationLogDTO;
import com.rngay.feign.authority.query.OperationLogPageQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationLogDao extends BaseMapper<OperationLogDTO> {

    Page<OperationLogDTO> pageOperationLog(Page<OperationLogDTO> page, @Param("pageList") OperationLogPageQuery operationLogPageQuery);

    int deleteOperationLog(@Param("array") List<Long> arrayQuery, @Param("orgId") Long orgId);

    int clear(@Param("orgId") Long orgId);

}
