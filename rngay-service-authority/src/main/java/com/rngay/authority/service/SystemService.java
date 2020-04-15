package com.rngay.authority.service;

import com.rngay.feign.authority.MenuDTO;
import com.rngay.feign.user.dto.UaUserDTO;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface SystemService {

    /**
     * 加载指定用户的菜单数据
     * @Author pengcheng
     * @Date 2019/3/27
     **/
    List<MenuDTO> listForMenu(UaUserDTO userDTO, Integer filed);

    /**
     * 查询指定用户允许访问的 url地址
     * @Author pengcheng
     * @Date 2019/3/26
     **/
    Set<String> getUrlSet(UaUserDTO userDTO);

    /**
     * 保存用户 token 到数据库并更新 redis 缓存
     * @Author: pengcheng
     * @Date: 2018/12/14
     */
    void insertToken(UaUserDTO userDTO, String token, List<String> authorities);

    /**
     * 从数据库删除 token 并更新 redis 缓存
     * @Author: pengcheng
     * @Date: 2018/12/15
     */
    Boolean deleteToken(Long userId);

    /**
     * 从数据库查询指定用户的有效 token
     * @Author: pengcheng
     * @Date: 2018/12/15
     */
    String findToken(Long userId, Date date);

    /**
     * 获取当前登录用户
     * @Author: pengcheng
     * @Date: 2018/12/16
     */
    UaUserDTO getCurrentUser();

    /**
     * 更新当前用户 redis 缓存
     * @Author pengcheng
     * @Date 2019/3/27
     **/
    int updateCurrentUser(UaUserDTO userDTO);

    /**
     * 获取当前登录用户 id
     * @Author: pengcheng
     * @Date: 2018/12/19
     */
    Long getCurrentUserId();

    /**
     * 获取当前登录用户的 orgId
     * @Author pengcheng
     * @Date 2019/3/30
     **/
    Long getCurrentOrgId();

}
