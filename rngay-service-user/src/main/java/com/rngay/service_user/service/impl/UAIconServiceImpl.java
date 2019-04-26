package com.rngay.service_user.service.impl;

import com.rngay.feign.user.dto.UAIconDTO;
import com.rngay.service_user.dao.UAIconDao;
import com.rngay.service_user.service.UAIconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UAIconServiceImpl implements UAIconService {

    @Autowired
    private UAIconDao iconDao;

    @Override
    public List<UAIconDTO> loadIcon() {
        return iconDao.loadIcon();
    }
}
