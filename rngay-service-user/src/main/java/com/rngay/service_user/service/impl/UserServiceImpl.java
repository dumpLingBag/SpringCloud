package com.rngay.service_user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.feign.user.dto.UAUserDTO;
import com.rngay.feign.user.dto.UAUserPageListDTO;
import com.rngay.feign.user.dto.UpdatePassword;
import com.rngay.service_user.dao.UserDao;
import com.rngay.service_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UAUserDTO findUserById(Long id) {
        return userDao.selectById(id);
    }

    @Override
    public UAUserDTO findUser(String username, String password) {
        QueryWrapper<UAUserDTO> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username).eq("password", password);
        return userDao.selectOne(wrapper);
    }

    @Override
    public UAUserDTO findByAccount(String username) {
        return userDao.selectOne(new QueryWrapper<UAUserDTO>().eq("username", username));
    }

    @Override
    public UAUserDTO findByMobile(String mobile) {
        return userDao.selectOne(new QueryWrapper<UAUserDTO>().eq("mobile", mobile));
    }

    @Override
    public int insertUser(UAUserDTO userDTO) {
        return userDao.insert(userDTO);
    }

    @Override
    public int updateUser(UAUserDTO userDTO) {
        return userDao.updateById(userDTO);
    }

    @Override
    public Page<UAUserDTO> pageList(UAUserPageListDTO pageListDTO) {
        Page<UAUserDTO> page = new Page<>(pageListDTO.getCurrentPage(), pageListDTO.getPageSize());
        return userDao.pageList(page, pageListDTO);
    }

    @Override
    public int reset(Long id) {
        UAUserDTO userDTO = new UAUserDTO();
        userDTO.setId(id);
        userDTO.setPassword(BCrypt.hashpw("123456", BCrypt.gensalt(12)));
        return userDao.updateById(userDTO);
    }

    @Override
    public int enable(Long id, Integer enable) {
        UAUserDTO userDTO = new UAUserDTO();
        userDTO.setId(id);
        userDTO.setEnable(enable);
        return userDao.updateById(userDTO);
    }

    @Override
    public int updatePassword(UpdatePassword password) {
        UAUserDTO userDTO = new UAUserDTO();
        userDTO.setId(password.getUserId());
        userDTO.setPassword(password.getPassword());
        return userDao.updateById(userDTO);
    }

    @Override
    public int delete(Long id) {
        return userDao.deleteById(id);
    }

}
