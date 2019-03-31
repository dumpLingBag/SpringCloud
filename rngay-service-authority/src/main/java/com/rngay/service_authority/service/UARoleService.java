package com.rngay.service_authority.service;

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

}
