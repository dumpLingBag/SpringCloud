package com.rngay.service_authority.service.impl;

import com.rngay.common.cache.RedisUtil;
import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.common.jpa.dao.SqlDao;
import com.rngay.feign.user.dto.UAUserDTO;
import com.rngay.service_authority.service.UASystemService;
import com.rngay.service_authority.util.AuthorityUtil;
import com.rngay.service_authority.util.JwtUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UASystemServiceImpl implements UASystemService {

    @Resource
    private Dao dao;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public int insertToken(HttpServletRequest request, UAUserDTO userDTO, String token) {
        Integer userId = userDTO.getId();

        Map<String, Object> map = new HashMap<>();
        map.put(".table", "ua_user_token");
        map.put("token", token);
        Date date = new Date();
        map.put("create_time", date);
        map.put("update_time", date);

        Date expireTime = jwtUtil.getExpiration(token);
        map.put("expire_time", expireTime);

        String key = request.getServerName() + "_" + userId;
        System.out.println("--->tokenKey:" + key);

        redisUtil.set(key, userDTO);

        int user_id = dao.update(map, Cnd.where("user_id", "=", userId));
        if (user_id > 0) {
            return user_id;
        } else {
            return dao.insert(map, "ua_user_token");
        }
    }

    @Override
    public int deleteToken(HttpServletRequest request, Integer userId) {
        String key = request.getServerName() + "_" + userId;
        redisUtil.del(key);
        return dao.update("update ua_user_token set token = null where user_id = ?", userId);
    }

    @Override
    public String findToken(Integer userId, Date date) {
        return dao.queryForObject("select token from ua_user_token where user_id = ? and expire_time > ?", String.class, userId, date);
    }

    @SuppressWarnings("unchecked")
    @Override
    public UAUserDTO getCurrentUser(HttpServletRequest request) {
        String token = AuthorityUtil.getRequestToken(request);
        if (token != null) {
            int userId = Integer.parseInt(jwtUtil.getSubject(token));
            String key = request.getServerName() + "_" + userId;
            return (UAUserDTO) redisUtil.get(key);
        }
        return null;
    }

    @Override
    public Integer getCurrentUserId(HttpServletRequest request) {
        UAUserDTO currentUser = getCurrentUser(request);
        if (currentUser != null) {
            return currentUser.getId();
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> loadForMenu(Map<String, Object> user) {
        Integer userId = (Integer) user.get("id");
        String account = (String) user.get("account");
        return null;
    }

}
