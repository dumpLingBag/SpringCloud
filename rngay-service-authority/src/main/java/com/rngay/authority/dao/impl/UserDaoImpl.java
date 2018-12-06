package com.rngay.authority.dao.impl;

import com.rngay.authority.dao.UserDao;
import com.rngay.common.jpa.SQLDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {

    @Resource
    private SQLDao sqlDao;

    @Override
    public int saveUser(Map<String, Object> map) {
        return sqlDao.insert("ua_user", map);
    }
}
