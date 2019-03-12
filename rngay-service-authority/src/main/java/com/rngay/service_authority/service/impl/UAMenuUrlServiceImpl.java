package com.rngay.service_authority.service.impl;

import com.rngay.common.jpa.dao.Dao;
import com.rngay.service_authority.service.UAMenuUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UAMenuUrlServiceImpl implements UAMenuUrlService {

    @Autowired
    private Dao dao;


    @Override
    public List<Map<String, Object>> load() {
        return null;
    }
}
