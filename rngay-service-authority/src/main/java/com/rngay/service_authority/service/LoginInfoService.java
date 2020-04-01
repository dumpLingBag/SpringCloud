package com.rngay.service_authority.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rngay.feign.authority.query.LoginInfoPageQuery;
import com.rngay.feign.authority.LoginInfoDTO;

public interface LoginInfoService extends IService<LoginInfoDTO> {

    Page<LoginInfoDTO> pageList(Page<LoginInfoDTO> page, LoginInfoPageQuery loginInfoPageQuery);

}
