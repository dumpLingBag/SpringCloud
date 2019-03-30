package com.rngay.service_authority.service.impl;

import com.rngay.common.jpa.dao.Dao;
import com.rngay.service_authority.dao.UARoleMenuDao;
import com.rngay.service_authority.model.UARole;
import com.rngay.service_authority.service.UARoleMenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UARoleMenuServiceImpl implements UARoleMenuService {

    @Resource
    private Dao dao;
    @Resource
    private UARoleMenuDao roleMenuDao;

    @Override
    public List<Map<String, Object>> load(Integer roleId) {
        UARole role = dao.findById(UARole.class, roleId);

        if (role == null) {
            return null;
        }
        Integer pid = role.getPid();
        if (pid > 0) {
            roleMenuDao.loadMenuByRoleId(pid);
        } else {
            roleMenuDao.loadMenuByOrgId(role.getOrgId());
        }
        return null;
    }

}
