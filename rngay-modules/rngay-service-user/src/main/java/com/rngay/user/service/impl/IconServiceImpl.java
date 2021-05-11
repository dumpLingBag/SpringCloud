package com.rngay.user.service.impl;

import com.rngay.feign.user.dto.UaIconDTO;
import com.rngay.user.dao.IconDao;
import com.rngay.user.service.IconService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IconServiceImpl implements IconService {

    @Autowired
    private IconDao iconDao;

    @Override
    public List<UaIconDTO> loadIcon() {
        return iconDao.selectList(null);
    }

}
