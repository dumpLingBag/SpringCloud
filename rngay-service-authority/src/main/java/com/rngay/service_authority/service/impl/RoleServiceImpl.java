package com.rngay.service_authority.service.impl;

import com.rngay.feign.platform.RoleDTO;
import com.rngay.feign.platform.RoleIdListDTO;
import com.rngay.service_authority.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Override
    public List<RoleDTO> load(Integer orgId) {
        return null;
    }

    @Override
    public List<RoleDTO> loadByPid(Integer orgId) {
        return null;
    }

    @Override
    public Integer save(RoleDTO uaRole) {
        return null;
    }

    @Override
    public Integer update(RoleDTO uaRole) {
        return null;
    }

    @Override
    public Integer delete(RoleIdListDTO listDTO) {
        return null;
    }
}
