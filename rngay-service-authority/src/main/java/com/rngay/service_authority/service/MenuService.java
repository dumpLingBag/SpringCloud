package com.rngay.service_authority.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rngay.common.enums.FiledEnum;
import com.rngay.feign.authority.MenuDTO;
import com.rngay.feign.authority.MenuIdListDTO;
import com.rngay.feign.authority.MenuInListDTO;

import java.util.List;

public interface MenuService extends IService<MenuDTO> {

    /**
     * 保存一个新的菜单
     * @Author: pengcheng
     * @Date: 2018/12/17
     */
    Integer insertMenu(MenuDTO uaMenu);

    /**
     * 获取全部菜单
     * @Author: pengcheng
     * @Date: 2018/12/18
     */
    List<MenuDTO> list();

    /**
     *
     * @Author: pengcheng
     * @Date: 2019/4/2
     */
    List<MenuDTO> listByPid();

    /**
     * 删除指定菜单，同时删除关联信息，角色菜单数据，菜单 URL 数据。
     * 如果要删除的数组长度大于一说明是删除全部包括子菜单。否则删除一个并修改 sort 排序
     * @Author pengcheng
     * @Date 2019/3/29
     **/
    Integer delete(MenuIdListDTO menuIdListDTO);

    /**
     * 根据 orgId 查询菜单列表
     * @Author: pengcheng
     * @Date: 2019/12/30
     */
    List<MenuDTO> listMenuByOrgId(Long orgId, FiledEnum filedEnum);

    /**
     * 根据 orgId 和 userId 查询菜单列表
     * @Author: pengcheng
     * @Date: 2019/12/30
     */
    List<MenuDTO> listMenuByUserId(Long orgId, Long userId, FiledEnum filedEnum);

    /**
     * 根据菜单 id 批量或单个隐藏显示菜单
     * @Author: pengcheng
     * @Date: 2019/12/30
     */
    Integer updateInMenu(MenuInListDTO menuIdListDTO);

    /**
     * 根据用户 id 加载权限
     * @Author: pengcheng
     * @Date: 2020/3/28
     */
    List<String> listUrlByUser(Long userId);

    /**
     * 根据 roleId 加载权限
     * @Author: pengcheng
     * @Date: 2020/4/3
     */
    List<MenuDTO> listAuth(List<Long> menuId);

}
