package com.rngay.service_authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rngay.feign.platform.OrgRoleDTO;
import com.rngay.feign.platform.UrlDTO;
import com.rngay.feign.platform.UserRoleDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlDao extends BaseMapper<UrlDTO> {

    List<String> loadUrlByOrgId(@Param("roleIds") List<OrgRoleDTO> roleIds);

    List<String> loadUrlByUserId(@Param("roleIds") List<UserRoleDTO> roleIds);

    List<String> loadUrlByOrgUserId(@Param("orgRoles") List<OrgRoleDTO> orgRoles, @Param("userRoleIds")List<UserRoleDTO> userRoleIds);

    int updateSql();

    List<String> loadUrlByList();

}
