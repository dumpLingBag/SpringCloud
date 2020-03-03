package com.rngay.service_authority.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rngay.feign.authority.LogPageDTO;
import com.rngay.feign.authority.LoginInfoDTO;
import com.rngay.service_authority.dao.LoginInfoDao;
import com.rngay.service_authority.service.LoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginInfoServiceImpl extends ServiceImpl<LoginInfoDao, LoginInfoDTO> implements LoginInfoService {

    @Autowired
    private LoginInfoDao loginInfoDao;

    @Override
    public Page<LoginInfoDTO> pageList(Page<LoginInfoDTO> page, LogPageDTO logPageDTO) {
        return loginInfoDao.pageList(page, logPageDTO);
    }
}
