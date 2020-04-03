package com.rngay.service_user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.feign.authority.UserRoleDTO;
import com.rngay.feign.user.dto.UaUserDTO;
import com.rngay.feign.user.dto.UaUserPageListDTO;
import com.rngay.feign.user.dto.UpdatePassword;

import java.util.List;

public interface UserService {

    /**
     * 根据用户id查询用户数据
     * @Author: pengcheng
     * @Date: 2018/12/13
     */
    UaUserDTO getUserById(Long id);

    /**
     * 根据用户名或手机号查询用户数据
     * @Author: pengcheng
     * @Date: 2018/12/13
     */
    UaUserDTO getUser(String account, String password);

    /**
     * 通过账号查询信息
     * @Author: pengcheng
     * @Date: 2019/2/2
     */
    UaUserDTO getUserByUsername(String username);

    /**
     * 通过手机号查询信息
     * @Author: pengcheng
     * @Date: 2019/2/2
     */
    UaUserDTO getUserByMobile(String mobile);

    /**
     * 添加一个用户
     * @Author: pengcheng
     * @Date: 2018/12/16
     */
    int insertUser(UaUserDTO userDTO);

    /**
     * 修改用户信息
     * @Author: pengcheng
     * @Date: 2018/12/28
     */
    int updateUser(UaUserDTO userDTO);

    /**
     * 分页获取用户信息
     * @Author: pengcheng
     * @Date: 2018/12/27
     */
    Page<UaUserDTO> page(UaUserPageListDTO pageListDTO);

    /**
     * 重置用户密码
     * @Author: pengcheng
     * @Date: 2018/12/29
     */
    String reset(Long id);

    /**
     * 启禁用账号
     * @Author: pengcheng
     * @Date: 2019/1/7
     */
    int enabled(Long id, Integer enable);

    /**
     * 修改用户密码
     * @Author: pengcheng
     * @Date: 2019/1/14
     */
    int updatePassword(UpdatePassword password);

    /**
     * 根据id删除用户
     * @Author: pengcheng
     * @Date: 2019/2/3
     */
    int delete(Long id);

    /**
     * 更新用户头像
     * @author pengcheng
     * @date 2020-03-07 17:23
     */
    int uploadAvatar(String path, Long userId);

    /**
     * 通过 userId 加载指定用户
     * @Author: pengcheng
     * @Date: 2020/3/13
     */
    List<UaUserDTO> loadByUserIds(List<UserRoleDTO> roleDTO);

}
