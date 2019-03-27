package com.rngay.service_authority.service.impl;

import com.rngay.common.cache.RedisUtil;
import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.feign.user.dto.UAUserDTO;
import com.rngay.service_authority.dao.UARoleMenuDao;
import com.rngay.service_authority.model.UAUserToken;
import com.rngay.service_authority.service.UASystemService;
import com.rngay.service_authority.util.AuthorityUtil;
import com.rngay.service_authority.util.JwtUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class UASystemServiceImpl implements UASystemService {

    @Resource
    private Dao dao;
    @Resource
    private UARoleMenuDao roleMenuDao;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private RedisUtil redisUtil;

    @Override
    public List<Map<String, Object>> loadForMenu(UAUserDTO userDTO) {
        if (userDTO == null) return null;

        List<Map<String, Object>> allMenus = new ArrayList<>();
        if ("admin".equals(userDTO.getAccount())) {
            List<Map<String, Object>> menuList = roleMenuDao.loadMenuByOrgId(userDTO.getOrgId());
            if (menuList != null && !menuList.isEmpty()) {
                allMenus.addAll(menuList);
            }
        } else {
            List<Map<String, Object>> menuList = roleMenuDao.loadMenuByUserId(userDTO.getOrgId(), userDTO.getId());
            if (menuList != null && !menuList.isEmpty()) {
                allMenus.addAll(menuList);
            }
        }

        if (allMenus.isEmpty()) return null;
        return menuList(allMenus);
    }

    private List<Map<String, Object>> menuList(List<Map<String, Object>> menuList) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Map<String, Object> menu : menuList) {
            if (menu.get("pid") == null || menu.get("pid").equals(0)) {
                menu.put("children", menuListChildren(menuList, menu));
                mapList.add(menu);
            }
        }
        return mapList;
    }

    private List<Map<String, Object>> menuListChildren(List<Map<String, Object>> menuList, Map<String, Object> menu) {
        List<Map<String, Object>> children = new ArrayList<>();
        for (Map<String, Object> pid : menuList) {
            if (pid.get("pid").equals(menu.get("id"))) {
                children.add(pid);
                menuListChildren(menuList, pid);
            }
        }
        return children;
    }

    @Override
    public Set<String> getUrlSet(UAUserDTO userDTO) {
        return null;
    }

    @Override
    public int insertToken(HttpServletRequest request, UAUserDTO userDTO, String token) {
        Integer userId = userDTO.getId();

        UAUserToken userToken = new UAUserToken();
        userToken.setUserId(userId);
        userToken.setToken(token);
        Date date = new Date();
        userToken.setUpdateTime(date);

        Date expireTime = jwtUtil.getExpiration(token);
        userToken.setExpireTime(expireTime);

        String key = request.getServerName() + "_" + userId;
        System.out.println("--->tokenKey:" + key);

        redisUtil.set(key, userDTO);

        int user_id = dao.update(userToken, Cnd.where("user_id", "=", userId));
        if (user_id > 0) {
            return user_id;
        } else {
            userToken.setCreateTime(date);
            return dao.insert(userToken);
        }
    }

    @Override
    public int deleteToken(HttpServletRequest request, Integer userId) {
        String key = request.getServerName() + "_" + userId;
        redisUtil.del(key);
        UAUserToken user = dao.find(UAUserToken.class, Cnd.where("user_id", "=", userId));
        user.setToken(null);
        return dao.update(user, true);
    }

    @Override
    public String findToken(Integer userId, Date date) {
        return dao.queryForString(UAUserToken.class, Cnd.where("user_id","=", userId).and("expire_time",">", date), "token");
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
    public int updateCurrentUser(HttpServletRequest request, UAUserDTO userDTO) {
        if (userDTO != null) {
            String key = request.getServerName() + "_" + userDTO.getId();
            redisUtil.set(key, userDTO);
            return 1;
        }
        return 0;
    }

    @Override
    public Integer getCurrentUserId(HttpServletRequest request) {
        UAUserDTO currentUser = getCurrentUser(request);
        if (currentUser != null) {
            return currentUser.getId();
        }
        return null;
    }

}
