package com.rngay.gateway.service;

import com.rngay.feign.user.dto.UAUserDTO;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.Date;

public interface SystemService {

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
    UAUserDTO getCurrentUser(ServerHttpRequest request);

}
