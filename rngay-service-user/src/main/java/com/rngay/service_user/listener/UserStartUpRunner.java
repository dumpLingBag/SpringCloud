package com.rngay.service_user.listener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rngay.common.util.CryptUtil;
import com.rngay.feign.user.dto.UAUserDTO;
import com.rngay.service_user.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class UserStartUpRunner implements CommandLineRunner {

    @Autowired
    private UserDao userDao;

    @Override
    public void run(String... args) throws Exception {
        Integer orgId = userDao.selectCount(new QueryWrapper<UAUserDTO>().eq("org_id", 0));
        if (orgId <= 0) {
            UAUserDTO userDTO = new UAUserDTO();
            userDTO.setUsername("admin");
            userDTO.setEnable(1);
            userDTO.setNickname("超级管理员");
            userDTO.setParentId(0L);
            userDTO.setPassword(BCrypt.hashpw(CryptUtil.MD5encrypt("123456"), BCrypt.gensalt(12)));
            userDTO.setCreateTime(new Date());
            userDTO.setUpdateTime(new Date());
            userDTO.setOrgId(0);
            userDao.insert(userDTO);
        }
    }

}
