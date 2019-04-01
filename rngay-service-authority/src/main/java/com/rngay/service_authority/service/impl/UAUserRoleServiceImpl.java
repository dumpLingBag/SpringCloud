package com.rngay.service_authority.service.impl;

import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.feign.platform.UserRoleUpdateDTO;
import com.rngay.service_authority.model.UAUserRole;
import com.rngay.service_authority.service.UAUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UAUserRoleServiceImpl implements UAUserRoleService {

    @Resource
    private Dao dao;

    @Override
    public List<UAUserRole> load(Integer userId) {
        return dao.query(UAUserRole.class, Cnd.where("checked","=",1).and("user_id","=", userId));
    }

    @Override
    public Integer update(UserRoleUpdateDTO updateDTO) {
        return null;
    }

}
