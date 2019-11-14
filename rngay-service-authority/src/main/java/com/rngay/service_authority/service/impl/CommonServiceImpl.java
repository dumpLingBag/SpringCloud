package com.rngay.service_authority.service.impl;

import com.rngay.feign.platform.CommonUrlDTO;
import com.rngay.service_authority.model.UAUrl;
import com.rngay.service_authority.service.CommonService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonServiceImpl implements CommonService {
    @Override
    public Integer update(CommonUrlDTO urlDTO) {
        return null;
    }

    @Override
    public List<UAUrl> loadOpen() {
        return null;
    }
}
