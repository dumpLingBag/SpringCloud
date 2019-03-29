package com.rngay.service_authority.dao.impl;

import com.rngay.common.jpa.dao.Dao;
import com.rngay.service_authority.dao.UAUserDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;

@Repository
public class UserDaoImpl implements UAUserDao {

    @Resource
    private Dao dao;

    @Override
    public int saveUser(Map<String, Object> map) {
        return dao.insert(map, "ua_user");
    }

}
