package com.rngay.service_authority.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rngay.feign.platform.UrlDTO;
import com.rngay.feign.platform.UserRoleDTO;
import com.rngay.feign.platform.UserRoleUpdateDTO;

import java.util.List;

public interface UserRoleService extends IService<UserRoleDTO> {

    /**
     * 根据 userId 加载用户拥有的权限
     * @Author pengcheng
     * @Date 2019/4/1
     **/
    List<UserRoleDTO> load(Integer userId);

    /**
     * 插入或更新用户角色，支持更新多个角色
     * @Author pengcheng
     * @Date 2019/4/1
     **/
    Integer update(UserRoleUpdateDTO updateDTO);

    List<String> loadUrlByOrgId(Integer orgId);

    List<String> loadUrlByUserId(Integer orgId, Integer userId);

}