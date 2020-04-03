package com.rngay.service_user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.common.util.CryptUtil;
import com.rngay.common.util.RandomPassUtil;
import com.rngay.feign.authority.UserRoleDTO;
import com.rngay.feign.user.dto.UaUserDTO;
import com.rngay.feign.user.dto.UaUserPageListDTO;
import com.rngay.feign.user.dto.UpdatePassword;
import com.rngay.service_user.dao.UserDao;
import com.rngay.service_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UaUserDTO getUserById(Long id) {
        return userDao.selectOne(new QueryWrapper<UaUserDTO>().eq("id", id).eq("del_flag", 1));
    }

    @Override
    public UaUserDTO getUser(String username, String password) {
        QueryWrapper<UaUserDTO> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username).eq("password", password).eq("del_flag", 1);
        return userDao.selectOne(wrapper);
    }

    @Override
    public UaUserDTO getUserByUsername(String username) {
        String reg = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Matcher matcher = Pattern.compile(reg).matcher(username);
        if (matcher.matches()) {
            return userDao.selectOne(new QueryWrapper<UaUserDTO>().eq("email", username).eq("del_flag", 1));
        }
        return userDao.selectOne(new QueryWrapper<UaUserDTO>().eq("username", username).eq("del_flag", 1));
    }

    @Override
    public UaUserDTO getUserByMobile(String mobile) {
        return userDao.selectOne(new QueryWrapper<UaUserDTO>().eq("mobile", mobile).eq("del_flag", 1));
    }

    @Override
    public int insertUser(UaUserDTO userDTO) {
        return userDao.insert(userDTO);
    }

    @Override
    public int updateUser(UaUserDTO userDTO) {
        return userDao.updateById(userDTO);
    }

    @Override
    public Page<UaUserDTO> page(UaUserPageListDTO pageListDTO) {
        Page<UaUserDTO> page = new Page<>(pageListDTO.getCurrentPage(), pageListDTO.getPageSize());
        return userDao.page(page, pageListDTO);
    }

    @Override
    public String reset(Long id) {
        UaUserDTO userDTO = new UaUserDTO();
        userDTO.setId(id);
        String pwd = RandomPassUtil.getRandomPwd(8);
        userDTO.setPassword(BCrypt.hashpw(CryptUtil.MD5encrypt(pwd), BCrypt.gensalt(12)));
        int i = userDao.updateById(userDTO);
        return i > 0 ? pwd : null;
    }

    @Override
    public int enabled(Long id, Integer enabled) {
        UaUserDTO userDTO = new UaUserDTO();
        userDTO.setId(id);
        userDTO.setEnabled(enabled);
        return userDao.updateById(userDTO);
    }

    @Override
    public int updatePassword(UpdatePassword password) {
        UaUserDTO userDTO = new UaUserDTO();
        userDTO.setId(password.getUserId());
        userDTO.setPassword(password.getPassword());
        return userDao.updateById(userDTO);
    }

    @Override
    public int delete(Long id) {
        return userDao.deleteById(id);
    }

    @Override
    public int uploadAvatar(String path, Long userId) {
        UaUserDTO userDTO = new UaUserDTO();
        userDTO.setAvatar(path);
        return userDao.update(userDTO, new QueryWrapper<UaUserDTO>().eq("id", userId));
    }

    @Override
    public List<UaUserDTO> loadByUserIds(List<UserRoleDTO> roleDTO) {
        return userDao.loadByUserIds(roleDTO);
    }
}
