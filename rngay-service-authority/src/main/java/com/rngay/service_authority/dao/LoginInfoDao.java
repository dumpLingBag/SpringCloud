package com.rngay.service_authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.feign.authority.query.LoginInfoPageQuery;
import com.rngay.feign.authority.LoginInfoDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginInfoDao extends BaseMapper<LoginInfoDTO> {

    Page<LoginInfoDTO> pageList(Page<LoginInfoDTO> page, @Param("pageList") LoginInfoPageQuery loginInfoPageQuery);

    int deleteLoginInfo(@Param("array") List<Long> arrayQuery, @Param("orgId") Long orgId);

    int clear(@Param("orgId") Long orgId);

}
