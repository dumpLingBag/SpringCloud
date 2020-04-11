package com.rngay.service_authority.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rngay.feign.authority.query.LoginInfoPageQuery;
import com.rngay.feign.authority.LoginInfoDTO;
import com.rngay.service_authority.dao.LoginInfoDao;
import com.rngay.service_authority.service.LoginInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginInfoServiceImpl extends ServiceImpl<LoginInfoDao, LoginInfoDTO> implements LoginInfoService {

    @Autowired
    private LoginInfoDao loginInfoDao;

    @Override
    public Page<LoginInfoDTO> pageLoginInfo(Page<LoginInfoDTO> page, LoginInfoPageQuery loginInfoPageQuery) {
        return loginInfoDao.pageLoginInfo(page, loginInfoPageQuery);
    }

    @Override
    public int delete(List<Long> arrayQuery, Long orgId) {
        return loginInfoDao.deleteLoginInfo(arrayQuery, orgId);
    }

    @Override
    public int clear(Long orgId) {
        return loginInfoDao.clear(orgId);
    }
}
