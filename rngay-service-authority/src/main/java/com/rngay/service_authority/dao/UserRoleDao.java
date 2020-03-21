package com.rngay.service_authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.feign.authority.UserRoleDTO;
import com.rngay.feign.authority.UserRoleParamDTO;
import com.rngay.feign.user.dto.UaUserDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleDao extends BaseMapper<UserRoleDTO> {

    /**
     * 根据 userId 加载角色信息
     * @Author: pengcheng
     * @Date: 2020/3/12
     */
    List<UserRoleDTO> getRoleId(@Param("userId") Long userId);

    /**
     * 根据 roleId 加载用户
     * @Author: pengcheng
     * @Date: 2020/3/12
     */
    List<UaUserDTO> loadUserByRoleId(List<Long> roleIds);

    /**
     * 根据 roles 查询用户
     * @Author: pengcheng
     * @Date: 2020/3/13
     */
    Page<UserRoleDTO> loadPageUserByRoleId(Page<UserRoleDTO> page, Long roleId);

    List<UserRoleDTO> loadRoleByUserId(@Param("userIds") List<UaUserDTO> userDTOS);

    void updateBatch(@Param("userRoleList") List<UserRoleDTO> userRoleList);

}
