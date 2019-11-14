package com.rngay.service_authority.service.impl;

import com.rngay.feign.platform.RoleMenuDTO;
import com.rngay.feign.platform.UpdateRoleMenuDTO;
import com.rngay.service_authority.service.RoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleMenuServiceImpl implements RoleMenuService {
    @Override
    public List<RoleMenuDTO> load(Integer orgId) {
        return null;
    }

    @Override
    public List<RoleMenuDTO> loadMenu(Integer roleId) {
        return null;
    }

    @Override
    public Integer update(UpdateRoleMenuDTO roleMenu) {
        return null;
    }
}
