package com.rngay.service_authority.service;

import com.rngay.feign.platform.RoleIdListDTO;
import com.rngay.service_authority.model.UARole;

import java.util.List;
import java.util.Map;

public interface UARoleService {

    /**
    * 根据 orgId 加载关联角色
    * @Author: pengcheng
    * @Date: 2019/3/30
    */
    List<Map<String, Object>> load(Integer orgId);

    /**
    * 根据 orgId 加载关联角色除去 pid 为零的信息
    * @Author pengcheng
    * @Date 2019/4/1
    **/
    List<UARole> loadByPid(Integer orgId);

    /**
    * 添加一个角色信息
    * @Author: pengcheng
    * @Date: 2019/3/30
    */
    Integer save(UARole uaRole);

    /**
    * 更新一个角色信息
    * @Author: pengcheng
    * @Date: 2019/3/30
    */
    Integer update(UARole uaRole);

    /**
    * 根据 roleId 删除关联菜单
    * @Author pengcheng
    * @Date 2019/4/1
    **/
    Integer delete(RoleIdListDTO listDTO);

}
