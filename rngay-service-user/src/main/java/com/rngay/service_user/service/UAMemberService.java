package com.rngay.service_user.service;

import com.rngay.feign.user.dto.UAMemberDTO;

public interface UAMemberService {

    UAMemberDTO findUser(String account);

}
