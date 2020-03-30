package com.rngay.service_user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.feign.authority.UserRoleDTO;
import com.rngay.feign.user.dto.UaUserDTO;
import com.rngay.feign.user.dto.UaUserPageListDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends BaseMapper<UaUserDTO> {

    Page<UaUserDTO> page(Page<UaUserDTO> page, @Param("pageList") UaUserPageListDTO pageListDTO);

    List<UaUserDTO> loadByUserIds(@Param("roleDTO") List<UserRoleDTO> roleDTO);

}
