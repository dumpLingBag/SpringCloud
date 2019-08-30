package com.rngay.service_user.service.impl;

import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.feign.user.dto.UAMemberDTO;
import com.rngay.service_user.service.UAMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UAMemberServiceImpl implements UAMemberService {

    @Autowired
    private Dao dao;

    @Override
    public UAMemberDTO findUser(String account) {
        return dao.find(UAMemberDTO.class, Cnd.where("account","=", account));
    }

}
