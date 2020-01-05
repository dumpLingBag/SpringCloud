package com.rngay.service_authority.service;

import com.rngay.feign.authority.RoleDTO;
import com.rngay.feign.authority.RoleIdListDTO;
import com.rngay.feign.authority.RoleInListDTO;

import java.util.List;

public interface RoleService {

    /**
     * 根据 orgId 加载关联角色
     * @Author: pengcheng
     * @Date: 2019/3/30
     */
    List<RoleDTO> load(Integer orgId);

    /**
     * 根据 orgId 加载出未被禁用的角色
     * @author pengcheng
     * @date 2020-01-05 18:00
     */
    List<RoleDTO> loadRole(Integer orgId);

    /**
     * 根据 orgId 加载关联角色除去 pid 为零的信息
     * @Author pengcheng
     * @Date 2019/4/1
     **/
    List<RoleDTO> loadByPid(Integer orgId);

    /**
     * 添加一个角色信息
     * @Author: pengcheng
     * @Date: 2019/3/30
     */
    Integer save(RoleDTO uaRole);

    /**
     * 更新一个角色信息
     * @Author: pengcheng
     * @Date: 2019/3/30
     */
    Integer update(RoleDTO uaRole);

    /**
     * 根据 roleId 删除关联菜单
     * @Author pengcheng
     * @Date 2019/4/1
     **/
    Integer delete(RoleIdListDTO listDTO);

    /**
     * 批量更新角色状态
     * @author pengcheng
     * @date 2020-01-05 15:20
     */
    Integer updateInList(RoleInListDTO roleInList);

}
