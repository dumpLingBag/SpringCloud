package com.rngay.service_user.service.impl;

import com.rngay.common.vo.PageList;
import com.rngay.feign.user.dto.*;
import com.rngay.service_user.dao.UAUserDao;
import com.rngay.service_user.service.UAUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UAUserServiceImpl implements UAUserService {
 
    @Resource
    private UAUserDao uaUserDao;

    @Override
    public UAUserDTO findUserById(Integer id) {
        return uaUserDao.findUserById(id);
    }

    @Override
    public UAUserDTO findUser(String account, String password) {
        return uaUserDao.findUser(account, password);
    }

    @Override
    public UAUserDTO findByAccount(String account) {
        return uaUserDao.findByAccount(account);
    }

    @Override
    public UAUserDTO findByMobile(String mobile) {
        return uaUserDao.findByMobile(mobile);
    }

    @Override
    public int insertUser(UASaveUserDTO userDTO) {
        return uaUserDao.insertUser(userDTO);
    }

    @Override
    public int updateUser(UAUpdateUserDTO userDTO) {
        return uaUserDao.updateUser(userDTO);
    }

    @Override
    public PageList<UAUserDTO> pageList(UAUserPageListDTO pageListDTO) {
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

    @Override
    public int delete(Integer id) {
        return uaUserDao.delete(id);
    }
}
