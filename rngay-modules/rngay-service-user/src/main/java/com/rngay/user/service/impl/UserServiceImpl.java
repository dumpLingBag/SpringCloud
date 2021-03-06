package com.rngay.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.common.util.CryptUtil;
import com.rngay.common.util.RandomPassUtil;
import com.rngay.common.util.StringUtils;
import com.rngay.feign.authority.UserRoleDTO;
import com.rngay.feign.dto.PageQueryDTO;
import com.rngay.feign.user.dto.UaUserDTO;
import com.rngay.feign.user.dto.UaUserPageListDTO;
import com.rngay.feign.user.dto.UpdatePassword;
import com.rngay.feign.user.query.PageUserQuery;
import com.rngay.feign.user.query.UserQuery;
import com.rngay.user.dao.UserDao;
import com.rngay.user.service.UserService;
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
    public List<UaUserDTO> list(UserQuery userQuery) {
        QueryWrapper<UaUserDTO> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(userQuery.getNickname())) {
            wrapper.like("nickname", userQuery.getNickname());
        }
        if (StringUtils.isNotBlank(userQuery.getUsername())) {
            wrapper.like("username", userQuery.getUsername());
        }
        if (StringUtils.isNotNull(userQuery.getEnabled())) {
            wrapper.eq("enabled", userQuery.getEnabled());
        }
        return userDao.selectList(wrapper);
    }

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
    public Page<UaUserDTO> page(PageUserQuery userQuery) {
        Page<UaUserDTO> page = new Page<>(userQuery.getCurrentPage(), userQuery.getPageSize());
        return userDao.page(page, userQuery);
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

    @Override
    public UaUserDTO getUserByEmail(String email) {
        QueryWrapper<UaUserDTO> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email).eq("del_flag", "1");
        return userDao.selectOne(wrapper);
    }
}
