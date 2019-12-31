package com.rngay.service_user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rngay.feign.user.dto.UaMemberDTO;
import com.rngay.service_user.dao.MemberDao;
import com.rngay.service_user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    @Override
    public UaMemberDTO findUser(String username) {
        return memberDao.selectOne(new QueryWrapper<UaMemberDTO>().eq("username",username));
    }

}
