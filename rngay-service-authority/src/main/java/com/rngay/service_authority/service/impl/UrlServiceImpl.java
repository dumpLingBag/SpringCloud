package com.rngay.service_authority.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rngay.feign.platform.UrlDTO;
import com.rngay.service_authority.dao.UrlDao;
import com.rngay.service_authority.service.UrlService;
import org.springframework.stereotype.Service;

@Service
public class UrlServiceImpl extends ServiceImpl<UrlDao, UrlDTO> implements UrlService {
}
