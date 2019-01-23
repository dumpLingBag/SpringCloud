package com.rngay.service_authority.dao.impl;

import com.rngay.common.jpa.dao.SqlDao;
import com.rngay.service_authority.dao.UserDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {

    @Resource
    private SqlDao sqlDao;

    @Override
    public int saveUser(Map<String, Object> map) {
        return sqlDao.insert("ua_user", map);
    }
}
