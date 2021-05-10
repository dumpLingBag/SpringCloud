package com.rngay.authority.service;

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
    List<MenuDTO> list(Long orgId, Integer filed);

    /**
     * 根据 roleId 加载菜单
     * @Author: pengcheng
     * @Date: 2019/3/31
     */
    List<RoleMenuDTO> listMenu(Long roleId);

    /**
     * 根据 roleId 插入或更新所选的菜单
     * @Author pengcheng
     * @Date 2019/4/1
     **/
    Boolean insert(UpdateRoleMenuQuery query);

    /**
     * 查询用户角色列表
     * @param roleId: 角色id
     * @return: java.util.List<com.rngay.feign.authority.RoleMenuDTO>
     * @date 2021/5/10 上午10:30
     * @auther dongpengcheng
     */
    List<RoleMenuDTO> listCheckMenu(Long roleId);

}
