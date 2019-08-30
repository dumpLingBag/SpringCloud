package com.rngay.service;

import com.rngay.feign.user.dto.UAMemberDTO;
import com.rngay.feign.user.dto.UAUserDTO;

import javax.servlet.http.HttpServletRequest;

public interface UASystemService {

    /**
    *
    * @Author pengcheng
    * @Date 2019/8/29
    **/
    int insertToken(HttpServletRequest request, UAMemberDTO memberDTO, String token);

}
