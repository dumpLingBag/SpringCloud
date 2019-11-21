package com.rngay.service_authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rngay.feign.authority.OrgRoleDTO;
import com.rngay.feign.authority.UrlDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrgRoleDao extends BaseMapper<OrgRoleDTO> {

    List<UrlDTO> loadUrlByOrgId(@Param("roleIds") List<OrgRoleDTO> roleIds);

}
