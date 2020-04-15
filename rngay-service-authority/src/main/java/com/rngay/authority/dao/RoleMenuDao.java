package com.rngay.authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rngay.feign.authority.RoleMenuDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleMenuDao extends BaseMapper<RoleMenuDTO> {

    List<RoleMenuDTO> listMenu(@Param("roleId") Long roleId);

    void updateBatch(@Param("roleMenuList") List<RoleMenuDTO> roleMenu);

    int updateMenu(@Param("roleId") Long roleId);

}
