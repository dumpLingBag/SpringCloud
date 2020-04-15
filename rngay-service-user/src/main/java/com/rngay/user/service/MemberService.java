package com.rngay.user.service;

import com.rngay.feign.user.dto.UaMemberDTO;

public interface MemberService {

    UaMemberDTO findUser(String username);

}
