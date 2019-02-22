package com.rngay.service_user.dao.impl;

import com.rngay.common.jpa.dao.Dao;
import com.rngay.feign.user.dto.UAIconDTO;
import com.rngay.service_user.dao.UAIconDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class UAIconDaoImpl implements UAIconDao {

    @Resource
    private Dao dao;

    @Override
    public List<UAIconDTO> loadIcon() {
        return dao.query(UAIconDTO.class);
    }

}
