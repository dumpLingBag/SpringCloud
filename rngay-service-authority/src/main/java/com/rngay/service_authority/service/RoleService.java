package com.rngay.service_authority.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rngay.feign.authority.RoleDTO;
import com.rngay.feign.authority.query.RoleIdListQuery;
import com.rngay.feign.authority.RoleInListDTO;
import com.rngay.feign.authority.RoleMenuAllDTO;
import com.rngay.feign.user.dto.UaUserDTO;

import java.util.List;

public interface RoleService extends IService<RoleDTO> {

    /**
     * 根据 orgId 加载关联角色
     * @Author: pengcheng
     * @Date: 2019/3/30
     */
    List<RoleDTO> list(Long orgId);

    /**
     * 根据用户加载角色
     * @author pengcheng
     * @date 2020-02-12 15:51
     */
    List<RoleDTO> loadUserRole(UaUserDTO userDTO);

    /**
     * 加载出所有没被禁用的角色
     * @author pengcheng
     * @date 2020-02-12 12:12
     */
    List<RoleMenuAllDTO> loadAllRole();

    /**
     * 根据权限加载角色
     * @Author: pengcheng
     * @Date: 2020/3/27
     */
    List<RoleMenuAllDTO> loadRoleByUrl(String url);

    /**
     * 根据 orgId 加载出未被禁用的角色
     * @author pengcheng
     * @date 2020-01-05 18:00
     */
    List<RoleDTO> listRole(Long orgId);

    /**
     * 根据 orgId 加载关联角色除去 pid 为零的信息
     * @Author pengcheng
     * @Date 2019/4/1
     **/
    List<RoleDTO> listByPid(Long orgId);

    /**
     * 根据 roleId 删除关联菜单
     * @Author pengcheng
     * @Date 2019/4/1
     **/
    Integer delete(RoleIdListQuery listDTO);

    /**
     * 批量更新角色状态
     * @author pengcheng
     * @date 2020-01-05 15:20
     */
    Integer updateInList(RoleInListDTO roleInList);

    /**
     * 添加角色信息
     * @author pengcheng
     * @date 2020-04-06 19:31
     */
    Integer insert(RoleDTO uaRole);

}
