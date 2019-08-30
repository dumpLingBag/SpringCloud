package com.rngay.service.impl;

import com.rngay.common.cache.RedisUtil;
import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.contants.RedisKeys;
import com.rngay.feign.user.dto.UAMemberDTO;
import com.rngay.model.UAMemberToken;
import com.rngay.service.UASystemService;
import com.rngay.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class UASystemServiceImpl implements UASystemService {

    @Autowired
    private Dao dao;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public int insertToken(HttpServletRequest request, UAMemberDTO memberDTO, String token) {
        Integer memberId = memberDTO.getId();

        UAMemberToken memberToken = new UAMemberToken();
        memberToken.setId(memberId);
        memberToken.setToken(token);
        Date date = new Date();
        memberToken.setUpdateTime(date);

        Date expireTime = jwtUtil.getExpiration(token);
        memberToken.setExpireTime(expireTime);

        String key = request.getServerName() + "_" + memberId;
        System.out.println("--->tokenKey:" + key);

        redisUtil.set(RedisKeys.getUserKey(key), memberDTO);
        int user_id = dao.update(memberToken, Cnd.where("user_id", "=", memberId));
        if (user_id > 0) {
            return user_id;
        } else {
            memberToken.setCreateTime(date);
            return dao.insert(memberToken);
        }
    }

}
