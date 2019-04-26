package com.rngay.service_user.listener;

import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.common.util.CryptUtil;
import com.rngay.feign.user.dto.UASaveUserDTO;
import com.rngay.feign.user.dto.UAUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class StartUpRunner implements CommandLineRunner {

    @Autowired
    private Dao dao;

    @Override
    public void run(String... args) throws Exception {
        long orgId = dao.count(UAUserDTO.class, Cnd.where("org_id", "=", 0));
        if (orgId <= 0) {
            UASaveUserDTO userDTO = new UASaveUserDTO();
            userDTO.setAccount("admin");
            userDTO.setEnable(1);
            userDTO.setName("超级管理员");
            userDTO.setPassword(BCrypt.hashpw(CryptUtil.MD5encrypt("123456"), BCrypt.gensalt(12)));
            userDTO.setCreate_time(new Date());
            userDTO.setUpdate_time(new Date());
            userDTO.setOrgId(0);
            dao.insert(userDTO);
        }
    }

}
