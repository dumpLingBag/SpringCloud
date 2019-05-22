package com.rngay.service_authority.service.impl;

import com.rngay.common.cache.RedisUtil;
import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.feign.platform.CommonUrlDTO;
import com.rngay.feign.platform.UrlDTO;
import com.rngay.service_authority.model.UAUrl;
import com.rngay.service_authority.service.UACommonService;
import com.rngay.service_authority.util.AuthorityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class UACommonServiceImpl implements UACommonService {

    @Autowired
    private Dao dao;
    @Autowired
    private RedisUtil redisUtil;

    @Transactional
    @Override
    public Integer update(CommonUrlDTO urlDTO) {
        if (urlDTO.getUrlId() != null && !urlDTO.getUrlId().isEmpty()) {
            int[] ints = dao.batchUpdate(urlDTO.getUrlId());
            if (ints.length > 0) {
                Object o = redisUtil.get(AuthorityUtil.APPLICATION_COMMON_URL_KEY);
                @SuppressWarnings("unchecked")
                Map<Object, Object> commonUrl = (Map<Object, Object>)o;
                for (UrlDTO url : urlDTO.getUrlId()) {
                    Map<String, Object> uaUrl = dao.findById("ua_url", Long.valueOf(url.getId()));
                    if (uaUrl != null && !uaUrl.isEmpty()) {
                        commonUrl.put(uaUrl.get("url"), uaUrl.get("common"));
                        redisUtil.set(AuthorityUtil.APPLICATION_COMMON_URL_KEY, commonUrl);
                    }
                }
            }
            return ints.length;
        }
        return 0;
    }

    @Override
    public List<UAUrl> loadOpen() {
        return dao.query(UAUrl.class, Cnd.where("common", "=", 1).and("pid", "<>", "null"));
    }

}
