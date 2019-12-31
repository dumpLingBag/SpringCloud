package com.rngay.service_user.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.feign.user.dto.UaUserDTO;
import com.rngay.feign.user.dto.UaUserPageListDTO;
import com.rngay.feign.user.dto.UpdatePassword;

public interface UserService {

    /**
     * 根据用户id查询用户数据
     * @Author: pengcheng
     * @Date: 2018/12/13
     */
    UaUserDTO findUserById(Long id);

    /**
     * 根据用户名或手机号查询用户数据
     * @Author: pengcheng
     * @Date: 2018/12/13
     */
    UaUserDTO findUser(String account, String password);

    /**
     * 通过账号查询信息
     * @Author: pengcheng
     * @Date: 2019/2/2
     */
    UaUserDTO findByAccount(String account);

    /**
     * 通过手机号查询信息
     * @Author: pengcheng
     * @Date: 2019/2/2
     */
    UaUserDTO findByMobile(String mobile);

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
    Page<UaUserDTO> pageList(UaUserPageListDTO pageListDTO);

    /**
     * 重置用户密码
     * @Author: pengcheng
     * @Date: 2018/12/29
     */
    int reset(Long id);

    /**
     * 启禁用账号
     * @Author: pengcheng
     * @Date: 2019/1/7
     */
    int enable(Long id, Integer enable);

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

}
