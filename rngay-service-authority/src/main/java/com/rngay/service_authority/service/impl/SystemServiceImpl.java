package com.rngay.service_authority.service.impl;

import com.rngay.feign.user.dto.UAUserDTO;
import com.rngay.service_authority.service.SystemService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class SystemServiceImpl implements SystemService {
    @Override
    public List<Map<String, Object>> loadForMenu(UAUserDTO userDTO) {
        return null;
    }

    @Override
    public Set<String> getUrlSet(UAUserDTO userDTO) {
        return null;
    }

    @Override
    public int insertToken(HttpServletRequest request, UAUserDTO userDTO, String token) {
        return 0;
    }

    @Override
    public int deleteToken(HttpServletRequest request, Integer userId) {
        return 0;
    }

    @Override
    public String findToken(Integer userId, Date date) {
        return null;
    }

    @Override
    public UAUserDTO getCurrentUser(HttpServletRequest request) {
        return null;
    }

    @Override
    public int updateCurrentUser(HttpServletRequest request, UAUserDTO userDTO) {
        return 0;
    }

    @Override
    public Integer getCurrentUserId(HttpServletRequest request) {
        return null;
    }

    @Override
    public Integer getCurrentOrgId(HttpServletRequest request) {
        return null;
    }
}
