package com.rngay.service_authority.service.impl;

import com.rngay.feign.platform.UserRoleDTO;
import com.rngay.feign.platform.UserRoleUpdateDTO;
import com.rngay.service_authority.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Override
    public List<UserRoleDTO> load(Integer userId) {
        return null;
    }

    @Override
    public Integer update(UserRoleUpdateDTO updateDTO) {
        return null;
    }
}
