package com.rngay.service_user.service;

import com.rngay.common.vo.PageList;
import com.rngay.feign.user.dto.UAUserPageListDTO;
import com.rngay.feign.user.dto.UpdatePassword;

import java.util.Map;

public interface UAUserService {

    /**
     * 根据用户id查询用户数据
     * @Author: pengcheng
     * @Date: 2018/12/13
     */
    Map<String, Object> findUserById(Integer id);

    /**
     * 根据用户名或手机号查询用户数据
     * @Author: pengcheng
     * @Date: 2018/12/13
     */
    Map<String, Object> findUser(String account, String password);

    /**
    * 通过账号查询信息
    * @Author: pengcheng
    * @Date: 2019/2/2
    */
    Map<String, Object> findByAccount(String account);

    /**
    * 通过手机号查询信息
    * @Author: pengcheng
    * @Date: 2019/2/2
    */
    Map<String, Object> findByMobile(String mobile);

    /**
     * 添加一个用户
     * @Author: pengcheng
     * @Date: 2018/12/16
     */
    int insertUser(Map<String, Object> map);

    /**
    * 修改用户信息
    * @Author: pengcheng
    * @Date: 2018/12/28
    */
    int updateUser(Map<String, Object> map);

    /**
    * 分页获取用户信息
    * @Author: pengcheng
    * @Date: 2018/12/27
    */
    PageList<Map<String, Object>> pageList(UAUserPageListDTO pageListDTO);

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
