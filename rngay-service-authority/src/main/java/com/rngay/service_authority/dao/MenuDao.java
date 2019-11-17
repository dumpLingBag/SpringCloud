package com.rngay.service_authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rngay.feign.platform.MenuDTO;
import com.rngay.feign.platform.OrgRoleDTO;
import com.rngay.feign.platform.UserRoleDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuDao extends BaseMapper<MenuDTO> {

    int updateSort(@Param("sorts") List<MenuDTO> sort);

    List<MenuDTO> loadMenuByOrgId(@Param("roles") List<OrgRoleDTO> roles);

    List<MenuDTO> loadMenuByOrgUserId(@Param("roles") List<OrgRoleDTO> roles, @Param("userRoles") List<UserRoleDTO> userRoleIds);

    List<MenuDTO> loadMenuByUserId(@Param("roleIds") List<UserRoleDTO> roleIds);

}
