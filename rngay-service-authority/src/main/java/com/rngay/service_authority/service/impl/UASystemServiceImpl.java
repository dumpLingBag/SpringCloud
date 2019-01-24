package com.rngay.service_authority.service.impl;

import com.rngay.common.cache.RedisUtil;
import com.rngay.common.jpa.dao.SqlDao;
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
    private SqlDao sqlDao;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public int insertToken(HttpServletRequest request, Map<String, Object> user, String token) {
        Object userId = user.get("id");

        Map<String, Object> map = new HashMap<>();
        map.put("user_id", userId);
        map.put("token", token);
        Date date = new Date();
        map.put("create_time", date);
        map.put("update_time", date);

        Date expireTime = jwtUtil.getExpiration(token);
        map.put("expire_time", expireTime);

        String key = request.getServerName() + "_" + userId;
        System.out.println("--->tokenKey:" + key);

        redisUtil.set(key, user);

        return sqlDao.insertOrUpdate("ua_user_token", map, "user_id");
    }

    @Override
    public int deleteToken(HttpServletRequest request, Integer userId) {
        String key = request.getServerName() + "_" + userId;
        redisUtil.del(key);
        return sqlDao.update("update ua_user_token set token = null where user_id = ?", userId);
    }

    @Override
    public String findToken(Integer userId, Date date) {
        return sqlDao.queryForObject("select token from ua_user_token where user_id = ? and expire_time > ?", String.class, userId, date);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> getCurrentUser(HttpServletRequest request) {
        String token = AuthorityUtil.getRequestToken(request);
        if (token != null) {
            int userId = Integer.parseInt(jwtUtil.getSubject(token));
            String key = request.getServerName() + "_" + userId;
            return (Map<String, Object>) redisUtil.get(key);
        }
        return null;
    }

    @Override
    public Integer getCurrentUserId(HttpServletRequest request) {
        Map<String, Object> currentUser = getCurrentUser(request);
        if (currentUser != null) {
            return (Integer) currentUser.get("id");
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> loadForMenu(Map<String, Object> user) {
        Integer userId = (Integer) user.get("id");
        String account = (String) user.get("account");
        return null;
    }

    @Override
    public List<Map<String, Object>> loadIcon() {
        return null;
    }

}
