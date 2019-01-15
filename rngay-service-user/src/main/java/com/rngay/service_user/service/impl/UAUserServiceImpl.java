package com.rngay.service_user.service.impl;

import com.rngay.common.vo.PageList;
import com.rngay.feign.user.dto.UAUserPageListDTO;
import com.rngay.feign.user.dto.UpdatePassword;
import com.rngay.service_user.dao.UAUserDao;
import com.rngay.service_user.service.UAUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class UAUserServiceImpl implements UAUserService {
 
    @Resource
    private UAUserDao uaUserDao;

    @Override
    public Map<String, Object> findUserById(Integer id) {
        return uaUserDao.findUserById(id);
    }

    @Override
    public Map<String, Object> findUser(String account, String password) {
        return uaUserDao.findUser(account, password);
    }

    @Override
    public int insertUser(Map<String, Object> map) {
        return uaUserDao.insertUser(map);
    }

    @Override
    public int updateUser(Map<String, Object> map) {
        return uaUserDao.updateUser(map);
    }

    @Override
    public PageList<Map<String, Object>> pageList(UAUserPageListDTO pageListDTO) {
        return uaUserDao.pageList(pageListDTO);
    }

    @Override
    public int reset(Integer id) {
        return uaUserDao.reset(id);
    }

    @Override
    public int enable(Integer id, Integer enable) {
        return uaUserDao.enable(id, enable);
    }

    @Override
    public int updatePassword(UpdatePassword password) {
        return uaUserDao.updatePassword(password);
    }
}
