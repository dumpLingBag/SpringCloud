package com.rngay.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rngay.authority.constant.Constant;
import com.rngay.common.cache.RedisUtil;
import com.rngay.common.contants.RedisKeys;
import com.rngay.common.util.AuthorityUtil;
import com.rngay.common.util.JwtUtil;
import com.rngay.feign.authority.MenuDTO;
import com.rngay.feign.authority.UserTokenDTO;
import com.rngay.feign.user.dto.UaUserDTO;
import com.rngay.authority.dao.UserTokenDao;
import com.rngay.authority.service.MenuService;
import com.rngay.authority.service.SystemService;
import com.rngay.authority.service.UserRoleService;
import com.rngay.authority.util.MenuUtil;
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
    @Autowired
    private HttpServletRequest request;

    @Override
    public List<MenuDTO> listForMenu(UaUserDTO userDTO, Integer filed) {
        if (userDTO == null) {
            return null;
        }

        List<MenuDTO> allMenus = new ArrayList<>();
        if (userDTO.getParentId() != null && userDTO.getParentId() == 0) {
            List<MenuDTO> menuList = menuService.listMenuByOrgId(userDTO.getOrgId(), filed);
            if (menuList != null && !menuList.isEmpty()) {
                allMenus.addAll(menuList);
            }
        } else {
            List<MenuDTO> menuList = menuService.listMenuByUserId(userDTO.getOrgId(), userDTO.getId(), filed);
            if (menuList != null && !menuList.isEmpty()) {
                allMenus.addAll(menuList);
            }
        }
        if (allMenus.isEmpty()) {
            return null;
        }
        return MenuUtil.menuList(allMenus, Constant.MENU_TYPE_VO);
    }

    @Override
    public Set<String> getUrlSet(UaUserDTO userDTO) {
        if (userDTO == null) {
            return new HashSet<>();
        }
        Set<String> urlSet = new HashSet<>();

        if (userDTO.getParentId() != null && userDTO.getParentId() == 0) {
            List<String> urlList = userRoleService.listUrlByOrgId(userDTO.getOrgId());
            if (urlList != null && !urlList.isEmpty()) {
                urlSet.addAll(urlList);
            }
        } else {
            List<String> urlList = userRoleService.listUrlByUserId(userDTO.getOrgId(), userDTO.getId());
            if (urlList != null && !urlList.isEmpty()) {
                urlSet.addAll(urlList);
            }
        }
        return urlSet;
    }

    @Override
    public void insertToken(UaUserDTO userDTO, String token, List<String> authorities) {
        Long userId = userDTO.getId();
        redisUtil.zHashPut(RedisKeys.getUserKey(userId), Constant.ACCESS_TOKEN, token);
        redisUtil.zHashPut(RedisKeys.getUserKey(userId), Constant.CHECKED, userDTO.getChecked());
        redisUtil.zHashPut(RedisKeys.getUserKey(userId), Constant.USER_INFO, userDTO);
        redisUtil.zHashPut(RedisKeys.getUserKey(userId), Constant.AUTHORITIES, authorities);
        redisUtil.expire(RedisKeys.getUserKey(userId), userDTO.getChecked() ? 604800 : 7200);
    }

    @Override
    public Boolean deleteToken(Long userId) {
        return redisUtil.del(RedisKeys.getUserKey(userId));
    }

    @Override
    public String findToken(Long userId, Date date) {
        UserTokenDTO userTokenDTO = userTokenDao.selectOne(new QueryWrapper<UserTokenDTO>()
                .eq("user_id", userId).gt("expire_time", date));
        return userTokenDTO.getToken();
    }

    @Override
    public UaUserDTO getCurrentUser() {
        String token = AuthorityUtil.getRequestToken(request);
        if (token != null) {
            long userId = Long.parseLong(jwtUtil.getSubject(token));
            Object user = redisUtil.getHashVal(RedisKeys.getUserKey(userId), Constant.USER_INFO);
            if (user == null) {
                return null;
            }
            return (UaUserDTO) user;
        }
        return null;
    }

    @Override
    public int updateCurrentUser(UaUserDTO userDTO) {
        if (userDTO != null) {
            redisUtil.zHashPut(RedisKeys.getUserKey(userDTO.getId()), Constant.USER_INFO, userDTO);
            return 1;
        }
        return 0;
    }

    @Override
    public Long getCurrentUserId() {
        UaUserDTO currentUser = getCurrentUser();
        if (currentUser != null) {
            return currentUser.getId();
        }
        return null;
    }

    @Override
    public Long getCurrentOrgId() {
        UaUserDTO currentUser = getCurrentUser();
        if (currentUser != null && currentUser.getOrgId() != null) {
            return currentUser.getOrgId();
        }
        return 0L;
    }

}
