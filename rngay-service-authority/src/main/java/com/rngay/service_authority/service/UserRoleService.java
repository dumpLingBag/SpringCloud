package com.rngay.service_authority.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rngay.feign.authority.UserRoleDTO;
import com.rngay.feign.authority.UserRoleParamDTO;
import com.rngay.feign.authority.query.UserRoleClearQuery;
import com.rngay.feign.authority.query.UserRoleUpdateQuery;
import com.rngay.feign.user.dto.UaUserDTO;

import java.util.List;

public interface UserRoleService extends IService<UserRoleDTO> {

    /**
     * 根据 userId 加载用户拥有的权限
     * @Author pengcheng
     * @Date 2019/4/1
     **/
    List<UserRoleDTO> list(Long userId);

    /**
     * 插入或更新用户角色，支持更新多个角色
     * @Author pengcheng
     * @Date 2019/4/1
     **/
    Boolean insert(UserRoleUpdateQuery query);

    /**
     * 根据 orgId 加载权限
     * @Author: pengcheng
     * @Date: 2020/3/12
     */
    List<String> listUrlByOrgId(Long orgId);

    /**
     * 根据 orgId 和 userId 加载权限
     * @Author: pengcheng
     * @Date: 2020/3/12
     */
    List<String> listUrlByUserId(Long orgId, Long userId);

    /**
     * 根据 roleId 加载用户
     * @Author: pengcheng
     * @Date: 2020/3/12
     */
    Page<UaUserDTO> pageUserByRoleId(UserRoleParamDTO userRoleParamDTO);

    /**
     * 批量删除角色
     * @author pengcheng
     * @date 2020-04-06 22:13
     */
    Integer clearRole(UserRoleClearQuery clearQuery);

}
