package com.rngay.service_authority.service.impl;

import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.service_authority.model.UARole;
import com.rngay.service_authority.service.UARoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UARoleServiceImpl implements UARoleService {

    @Resource
    private Dao dao;

    @Override
    public List<UARole> load(Integer orgId) {
        return dao.query(UARole.class, Cnd.where("org_id","=", orgId).asc("sort"));
    }

    @Override
    public Integer save(UARole uaRole) {
        return dao.insert(uaRole);
    }

    @Override
    public Integer update(UARole uaRole) {
        return dao.update(uaRole);
    }

}
