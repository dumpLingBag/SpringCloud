package com.rngay.service_authority.dao.impl;

import com.rngay.service_authority.dao.UserDao;
import com.rngay.common.jpa.dao.SQLDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SQLDao sqlDao;

    @Override
    public int saveUser(Map<String, Object> map) {
        return sqlDao.insert("ua_user", map);
    }
}
