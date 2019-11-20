package com.rngay.service_user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rngay.feign.user.dto.UAMemberDTO;
import com.rngay.service_user.dao.MemberDao;
import com.rngay.service_user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public UAMemberDTO findUser(String username) {
        return memberDao.selectOne(new QueryWrapper<UAMemberDTO>().eq("username",username));
    }

}