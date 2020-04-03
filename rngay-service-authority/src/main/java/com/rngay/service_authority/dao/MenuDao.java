package com.rngay.service_authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rngay.feign.authority.MenuDTO;
import com.rngay.feign.authority.MenuInListDTO;
import com.rngay.feign.authority.OrgRoleDTO;
import com.rngay.feign.authority.UserRoleDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuDao extends BaseMapper<MenuDTO> {

    int updateSort(@Param("sorts") List<MenuDTO> sort);

    List<MenuDTO> loadMenuByOrgId(@Param("roles") List<OrgRoleDTO> roles, @Param("code") int code);

    List<MenuDTO> loadMenuByOrgUserId(@Param("roles") List<OrgRoleDTO> roles, @Param("userRoles") List<UserRoleDTO> userRoleIds, @Param("code") int code);

    List<MenuDTO> loadMenuByUserId(@Param("roleIds") List<UserRoleDTO> roleIds, @Param("code") int code);

    int updateInMenu(@Param("menuList") MenuInListDTO menuList);

    List<String> loadUrlByUser(@Param("userId") Long userId);

    List<MenuDTO> listAuth(@Param("menuIds") List<Long> menuId);

}
