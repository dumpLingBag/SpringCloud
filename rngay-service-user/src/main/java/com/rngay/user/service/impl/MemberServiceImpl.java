package com.rngay.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rngay.feign.user.dto.UaMemberDTO;
import com.rngay.user.dao.MemberDao;
import com.rngay.user.service.MemberService;
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
