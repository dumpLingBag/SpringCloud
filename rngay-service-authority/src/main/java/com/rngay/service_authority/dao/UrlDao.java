package com.rngay.service_authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rngay.feign.authority.OrgRoleDTO;
import com.rngay.feign.authority.UrlDTO;
import com.rngay.feign.authority.UserRoleDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlDao extends BaseMapper<UrlDTO> {

    List<String> listUrlByOrgId(@Param("roleIds") List<OrgRoleDTO> roleIds);

    List<String> listUrlByUserId(@Param("roleIds") List<UserRoleDTO> roleIds);

    List<String> listUrlByOrgUserId(@Param("orgRoles") List<OrgRoleDTO> orgRoles, @Param("userRoleIds")List<UserRoleDTO> userRoleIds);

    int updateSql();

    List<String> listUrl();

}
