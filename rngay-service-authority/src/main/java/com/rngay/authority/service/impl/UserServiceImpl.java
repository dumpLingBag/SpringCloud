package com.rngay.authority.service.impl;

import com.rngay.authority.dao.UserDao;
import com.rngay.authority.service.UserService;
import com.rngay.common.jpa.SQLDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserDao userDao;
    @Resource
    private SQLDao sqlDao;

    @Override
    public int saveUser(Map<String, Object> map) {
        return sqlDao.insert("ua_user", map);
    }
}
