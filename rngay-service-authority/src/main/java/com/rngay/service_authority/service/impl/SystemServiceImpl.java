package com.rngay.service_authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rngay.common.cache.RedisUtil;
import com.rngay.common.contants.RedisKeys;
import com.rngay.common.util.AuthorityUtil;
import com.rngay.common.util.JwtUtil;
import com.rngay.feign.authority.MenuDTO;
import com.rngay.feign.authority.UserTokenDTO;
import com.rngay.feign.user.dto.UAUserDTO;
import com.rngay.service_authority.dao.UserTokenDao;
import com.rngay.service_authority.service.MenuService;
import com.rngay.service_authority.service.SystemService;
import com.rngay.service_authority.service.UserRoleService;
import com.rngay.service_authority.util.MenuUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class SystemServiceImpl implements SystemService {

    @Autowired
    private MenuService menuService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UserTokenDao userTokenDao;

    @Override
    public List<MenuDTO> loadForMenu(UAUserDTO userDTO) {
        if (userDTO == null) return null;

        List<MenuDTO> allMenus = new ArrayList<>();
        if ("admin".equals(userDTO.getUsername())) {
            List<MenuDTO> menuList = menuService.loadMenuByOrgId(userDTO.getOrgId());
            if (menuList != null && !menuList.isEmpty()) {
                allMenus.addAll(menuList);
            }
        } else {
            List<MenuDTO> menuList = menuService.loadMenuByUserId(userDTO.getOrgId(), userDTO.getId());
            if (menuList != null && !menuList.isEmpty()) {
                allMenus.addAll(menuList);
            }
        }
        if (allMenus.isEmpty()) return null;
        return MenuUtil.menuList(allMenus, 1);
    }

    @Override
    public Set<String> getUrlSet(UAUserDTO userDTO) {
        if (userDTO == null) return new HashSet<>();
        Set<String> urlSet = new HashSet<>();

        if (userDTO.getUsername() != null && "admin".equals(userDTO.getUsername())) {
            List<String> urlList = userRoleService.loadUrlByOrgId(userDTO.getOrgId());
            if (urlList != null && !urlList.isEmpty()) {
                urlSet.addAll(urlList);
            }
        } else {
            List<String> urlList = userRoleService.loadUrlByUserId(userDTO.getOrgId(), userDTO.getId());
            if (urlList != null && !urlList.isEmpty()) {
                urlSet.addAll(urlList);
            }
        }
        return urlSet;
    }

    @Override
    public int insertToken(HttpServletRequest request, UAUserDTO userDTO, String token) {
        Long userId = userDTO.getId();

        UserTokenDTO userToken = new UserTokenDTO();
        userToken.setUserId(userId);
        userToken.setToken(token);
        Date date = new Date();
        userToken.setUpdateTime(date);

        Date expireTime = jwtUtil.getExpiration(token);
        userToken.setExpireTime(expireTime);

        redisUtil.set(RedisKeys.getUserKey(userId), userDTO);

        int user_id = userTokenDao.update(userToken, new QueryWrapper<UserTokenDTO>().eq("user_id", userId));
        if (user_id > 0) {
            return user_id;
        } else {
            userToken.setCreateTime(date);
            return userTokenDao.insert(userToken);
        }
    }

    @Override
    public int deleteToken(HttpServletRequest request, Long userId) {
        String key = request.getServerName() + "_" + userId;
        redisUtil.del(RedisKeys.getUserKey(key));
        UserTokenDTO user = userTokenDao.selectOne(new QueryWrapper<UserTokenDTO>().eq("user_id", userId));
        user.setToken(null);
        return userTokenDao.updateById(user);
    }

    @Override
    public String findToken(Long userId, Date date) {
        UserTokenDTO userTokenDTO = userTokenDao.selectOne(new QueryWrapper<UserTokenDTO>()
                .eq("user_id", userId).gt("expire_time", date));
        return userTokenDTO.getToken();
    }

    @Override
    public UAUserDTO getCurrentUser(HttpServletRequest request) {
        String token = AuthorityUtil.getRequestToken(request);
        if (token != null) {
            long userId = Long.parseLong(jwtUtil.getSubject(token));
            Object user = redisUtil.get(RedisKeys.getUserKey(userId));
            if (user == null) return null;
            return (UAUserDTO) user;
        }
        return null;
    }

    @Override
    public int updateCurrentUser(HttpServletRequest request, UAUserDTO userDTO) {
        if (userDTO != null) {
            redisUtil.set(RedisKeys.getUserKey(userDTO.getId()), userDTO);
            return 1;
        }
        return 0;
    }

    @Override
    public Long getCurrentUserId(HttpServletRequest request) {
        UAUserDTO currentUser = getCurrentUser(request);
        if (currentUser != null) {
            return currentUser.getId();
        }
        return null;
    }

    @Override
    public Integer getCurrentOrgId(HttpServletRequest request) {
        UAUserDTO currentUser = getCurrentUser(request);
        if (currentUser != null && currentUser.getOrgId() != null) {
            return currentUser.getOrgId();
        }
        return 0;
    }

}
