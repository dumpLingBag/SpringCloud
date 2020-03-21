package com.rngay.service_authority.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rngay.feign.authority.MenuDTO;
import com.rngay.feign.authority.RoleMenuDTO;
import com.rngay.feign.authority.query.UpdateRoleMenuQuery;

import java.util.List;

public interface RoleMenuService extends IService<RoleMenuDTO> {

    /**
     * 根据 orgId 加载关联的菜单
     * @Author pengcheng
     * @Date 2019/3/30
     **/
    List<MenuDTO> load(Long orgId);

    /**
     * 根据 roleId 加载菜单
     * @Author: pengcheng
     * @Date: 2019/3/31
     */
    List<RoleMenuDTO> loadMenu(Long roleId);

    /**
     * 根据 roleId 插入或更新所选的菜单
     * @Author pengcheng
     * @Date 2019/4/1
     **/
    Boolean save(UpdateRoleMenuQuery query);

}
