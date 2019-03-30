package com.rngay.service_authority.service;

import java.util.List;
import java.util.Map;

public interface UARoleMenuService {

    /**
    * 根据 roleId 加载关联的菜单
    * @Author pengcheng
    * @Date 2019/3/30
    **/
    List<Map<String, Object>> load(Integer roleId);

}
