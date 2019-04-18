package com.rngay.service_authority.dao;

import java.util.List;

public interface UAUserRoleDao {

    /**
    * 根据 orgId 加载对应的权限
    * @Author pengcheng
    * @Date 2019/3/29
    **/
    List<String> loadUrlByOrgId(Integer orgId);

    /**
    * 根据 orgId 及 userId 加载对应的权限
    * @Author pengcheng
    * @Date 2019/3/29
    **/
    List<String> loadUrlByUserId(Integer orgId, Integer userId);

}
