package com.rngay.service_user.dao.impl;

import com.rngay.common.jpa.dao.Dao;
import com.rngay.service_user.dao.UAIconDao;
import com.rngay.service_user.model.UAIcons;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class UAIconDaoImpl implements UAIconDao {

    @Resource
    private Dao dao;

    @Override
    public List<UAIcons> loadIcon() {
        return dao.query(UAIcons.class);
    }

}
