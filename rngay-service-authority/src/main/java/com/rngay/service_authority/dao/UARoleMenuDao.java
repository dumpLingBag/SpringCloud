package com.rngay.service_authority.dao;

import java.util.List;
import java.util.Map;

public interface UARoleMenuDao {

    /**
    * 根据 orgId 以及账号类型加载菜单
    * @Author pengcheng
    * @Date 2019/3/28
    **/
    List<Map<String, Object>> loadMenuByOrgId(Integer orgId);

    /**
    * 根据 orgId 及机构下的用户 id 加载对应菜单
    * @Author pengcheng
    * @Date 2019/3/28
    **/
    List<Map<String, Object>> loadMenuByUserId(Integer orgId, Integer userId);

}
