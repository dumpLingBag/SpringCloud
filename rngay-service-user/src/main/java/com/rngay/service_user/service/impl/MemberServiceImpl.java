package com.rngay.service_user.service.impl;

import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.feign.user.dto.UAMemberDTO;
import com.rngay.service_user.service.MemberService;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    private Dao dao;

    @Override
    public UAMemberDTO findUser(String account, String password) {
        return dao.find(UAMemberDTO.class, Cnd.where("account","=", account).and("password","=", password));
    }

}
