package com.rngay.gateway.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rngay.common.cache.RedisUtil;
import com.rngay.common.contants.RedisKeys;
import com.rngay.common.util.JwtUtil;
import com.rngay.gateway.dao.UserTokenDao;
import com.rngay.gateway.service.SystemService;
import com.rngay.feign.authority.UserTokenDTO;
import com.rngay.feign.user.dto.UAUserDTO;
import com.rngay.gateway.util.TokenUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private UserTokenDao userTokenDao;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String findToken(Long userId, Date date) {
        UserTokenDTO userTokenDTO = userTokenDao.selectOne(new QueryWrapper<UserTokenDTO>()
                .eq("user_id", userId).gt("expire_time", date));
        return userTokenDTO.getToken();
    }

    @Override
    public UAUserDTO getCurrentUser(ServerHttpRequest request) {
        String token = TokenUtil.getRequestToken(request);
        if (StringUtils.isNotBlank(token)) {
            long userId = Long.parseLong(jwtUtil.getSubject(token));
            Object user = redisUtil.get(RedisKeys.getUserKey(userId));
            return user == null ? null : (UAUserDTO) user;
        }
        return null;
    }
}
