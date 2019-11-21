package com.rngay.service_authority.service;

import com.rngay.feign.authority.MenuDTO;
import com.rngay.feign.authority.MenuIdListDTO;

import java.util.List;

public interface MenuService {

    /**
     * 保存一个新的菜单
     * @Author: pengcheng
     * @Date: 2018/12/17
     */
    Integer save(MenuDTO uaMenu);

    /**
     * 修改菜单
     * @Author pengcheng
     * @Date 2019/3/30
     **/
    Integer update(MenuDTO uaMenu);

    /**
     * 获取全部菜单
     * @Author: pengcheng
     * @Date: 2018/12/18
     */
    List<MenuDTO> load();

    /**
     *
     * @Author: pengcheng
     * @Date: 2019/4/2
     */
    List<MenuDTO> loadByPid();

    /**
     * 删除指定菜单，同时删除关联信息，角色菜单数据，菜单 URL 数据。
     * 如果要删除的数组长度大于一说明是删除全部包括子菜单。否则删除一个并修改 sort 排序
     * @Author pengcheng
     * @Date 2019/3/29
     **/
    Integer delete(MenuIdListDTO menuIdListDTO);

    List<MenuDTO> loadMenuByOrgId(Integer orgId);

    List<MenuDTO> loadMenuByUserId(Integer orgId, Long userId);

}
