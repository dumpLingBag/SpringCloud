package com.rngay.service_user.service.impl;

import com.rngay.service_user.dao.UAIconDao;
import com.rngay.service_user.service.UAIconService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class UAIconServiceImpl implements UAIconService {

    @Resource
    private UAIconDao iconDao;

    @Override
    public List<Map<String, Object>> loadIcon() {
        return iconDao.loadIcon();
    }
}
