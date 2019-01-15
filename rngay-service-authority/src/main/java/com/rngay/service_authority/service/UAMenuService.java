package com.rngay.service_authority.service;

import java.util.List;
import java.util.Map;

public interface UAMenuService {

    /**
    * 保存一个新的菜单
    * @Author: pengcheng
    * @Date: 2018/12/17
    */
    Integer save(String name, Integer pid, Integer sort);

    /**
    * 获取全部菜单
    * @Author: pengcheng
    * @Date: 2018/12/18
    */
    List<Map<String, Object>> getAllMenu();

    /**
    * 根据用户获取菜单
    * @Author: pengcheng
    * @Date: 2018/12/19
    */
    List<Map<String, Object>> getUserForMenu();

}
