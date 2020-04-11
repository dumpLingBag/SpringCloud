package com.rngay.service_authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rngay.feign.authority.RoleDTO;
import com.rngay.feign.authority.RoleInListDTO;
import com.rngay.feign.authority.RoleMenuAllDTO;
import com.rngay.feign.user.dto.UaUserDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao extends BaseMapper<RoleDTO> {

    int updateSort(@Param("sort") List<RoleDTO> sort);

    int updateInList(@Param("roleList") RoleInListDTO roleInList);

    List<RoleMenuAllDTO> listAllRole();

    List<RoleDTO> listUserRole(UaUserDTO uaUserDTO);

    List<RoleMenuAllDTO> listRoleByUrl(@Param("url") String url);

}
