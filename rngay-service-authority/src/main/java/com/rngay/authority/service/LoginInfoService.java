package com.rngay.authority.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rngay.feign.authority.query.LoginInfoPageQuery;
import com.rngay.feign.authority.LoginInfoDTO;

import java.util.List;

public interface LoginInfoService extends IService<LoginInfoDTO> {

    Page<LoginInfoDTO> pageLoginInfo(Page<LoginInfoDTO> page, LoginInfoPageQuery loginInfoPageQuery);

    int delete(List<Long> arrayQuery, Long orgId);

    int clear(Long orgId);

}
