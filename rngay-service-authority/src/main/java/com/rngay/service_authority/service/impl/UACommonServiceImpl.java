package com.rngay.service_authority.service.impl;

import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.feign.platform.CommonUrlDTO;
import com.rngay.service_authority.model.UAUrl;
import com.rngay.service_authority.service.UACommonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UACommonServiceImpl implements UACommonService {

    @Resource
    private Dao dao;

    @Transactional
    @Override
    public Integer update(CommonUrlDTO urlDTO) {
        if (urlDTO.getUrlId() != null && !urlDTO.getUrlId().isEmpty()) {
            int[] ints = dao.batchUpdate(urlDTO.getUrlId());
            return ints.length;
        }
        return 0;
    }

    @Override
    public List<UAUrl> loadOpen() {
        return dao.query(UAUrl.class, Cnd.where("common","=",1).and("pid","<>","null"));
    }

}
