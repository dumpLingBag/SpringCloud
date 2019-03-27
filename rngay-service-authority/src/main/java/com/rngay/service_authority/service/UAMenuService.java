package com.rngay.service_authority.service;

import com.rngay.feign.platform.MenuIdListDTO;
import com.rngay.service_authority.model.UAMenu;

import java.util.List;
import java.util.Map;

public interface UAMenuService {

    /**
    * 保存一个新的菜单
    * @Author: pengcheng
    * @Date: 2018/12/17
    */
    Integer save(UAMenu uaMenu);

    /**
    * 获取全部菜单
    * @Author: pengcheng
    * @Date: 2018/12/18
    */
    List<Map<String, Object>> getAllMenu();

    Integer delete(MenuIdListDTO menuIdListDTO);

}
