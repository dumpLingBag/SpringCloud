package com.rngay.service_authority.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface UASystemService {

    /**
    * 保存用户 token 到数据库并更新 redis 缓存
    * @Author: pengcheng
    * @Date: 2018/12/14
    */
    int insertToken(HttpServletRequest request, Map<String, Object> map, String token);

    /**
    * 从数据库删除 token 并更新 redis 缓存
    * @Author: pengcheng
    * @Date: 2018/12/15
    */
    int deleteToken(HttpServletRequest request, Integer userId);

    /**
    * 从数据库查询指定用户的有效 token
    * @Author: pengcheng
    * @Date: 2018/12/15
    */
    String findToken(Integer userId, Date date);

    /**
    * 获取当前登录用户
    * @Author: pengcheng
    * @Date: 2018/12/16
    */
    Map<String, Object> getCurrentUser(HttpServletRequest request);

    /**
    * 获取当前登录用户 id
    * @Author: pengcheng
    * @Date: 2018/12/19
    */
    Integer getCurrentUserId(HttpServletRequest request);

    /**
    * 根据用户加载菜单
    * @Author: pengcheng
    * @Date: 2018/12/19
    */
    List<Map<String, Object>> loadForMenu(Map<String, Object> user);

    /**
    * 加载 icon 图标
    * @Author: pengcheng
    * @Date: 2018/12/22
    */
    List<Map<String, Object>> loadIcon();

}
