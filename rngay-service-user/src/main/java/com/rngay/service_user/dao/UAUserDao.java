package com.rngay.service_user.dao;

import com.rngay.common.vo.PageList;
import com.rngay.feign.user.dto.*;
import com.rngay.service_user.model.UAUser;
import org.springframework.data.domain.Page;

import java.util.Map;

public interface UAUserDao {

    /**
    * 根据用户id查询用户数据
    * @Author: pengcheng
    * @Date: 2018/12/13
    */
    UAUserDTO findUserById(Integer id);

    /**
    * 根据用户名或手机号查询用户数据
    * @Author: pengcheng
    * @Date: 2018/12/13
    */
    UAUserDTO findUser(String account, String password);

    /**
     * 通过账号查询信息
     * @Author: pengcheng
     * @Date: 2019/2/2
     */
    UAUserDTO findByAccount(String account);

    /**
     * 通过手机号查询信息
     * @Author: pengcheng
     * @Date: 2019/2/2
     */
    UAUserDTO findByMobile(String mobile);

    /**
    * 添加一个用户
    * @Author: pengcheng
    * @Date: 2018/12/16
    */
    int insertUser(UASaveUserDTO userDTO);

    /**
     * 修改用户信息
     * @Author: pengcheng
     * @Date: 2018/12/28
     */
    int updateUser(UAUpdateUserDTO userDTO);

    /**
    * 分页获取用户信息
    * @Author: pengcheng
    * @Date: 2018/12/27
    */
    PageList<UAUserDTO> pageList(UAUserPageListDTO pageListDTO);

    /**
     * 重置用户密码
     * @Author: pengcheng
     * @Date: 2018/12/29
     */
    int reset(Integer id);

    /**
     * 启禁用账号
     * @Author: pengcheng
     * @Date: 2019/1/7
     */
    int enable(Integer id, Integer enable);

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
    int delete(Integer id);

}
