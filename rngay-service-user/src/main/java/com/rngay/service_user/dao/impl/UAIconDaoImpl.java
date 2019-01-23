package com.rngay.service_user.dao.impl;

import com.rngay.common.jpa.util.SqlDao;
import com.rngay.service_user.dao.UAIconDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Repository
public class UAIconDaoImpl implements UAIconDao {

    @Resource
    private SqlDao sqlDao;

    @Override
    public List<Map<String, Object>> loadIcon() {
        return sqlDao.queryForList("select * from ua_icon");
    }
}
