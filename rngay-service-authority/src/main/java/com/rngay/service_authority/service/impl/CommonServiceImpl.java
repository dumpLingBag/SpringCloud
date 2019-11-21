package com.rngay.service_authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rngay.common.cache.RedisUtil;
import com.rngay.feign.authority.CommonUrlDTO;
import com.rngay.feign.authority.UrlDTO;
import com.rngay.service_authority.dao.UrlDao;
import com.rngay.service_authority.service.CommonService;
import com.rngay.service_authority.util.AuthorityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonServiceImpl extends ServiceImpl<UrlDao, UrlDTO> implements CommonService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UrlDao urlDao;

    @Override
    public Integer update(CommonUrlDTO urlDTO) {
        if (urlDTO.getUrlId() != null && !urlDTO.getUrlId().isEmpty()) {
            boolean b = updateBatchById(urlDTO.getUrlId());
            if (b) {
                Object o = redisUtil.get(AuthorityUtil.APPLICATION_COMMON_URL_KEY);
                UrlDTO commonUrl = (UrlDTO)o;
                for (UrlDTO url : urlDTO.getUrlId()) {
                    UrlDTO uu = urlDao.selectOne(new QueryWrapper<UrlDTO>().eq("id", url.getId()));
                    if (uu != null) {
                        commonUrl.setUrl(String.valueOf(uu.getCommon()));
                        redisUtil.set(AuthorityUtil.APPLICATION_COMMON_URL_KEY, commonUrl);
                    }
                }
            }
            return 1;
        }
        return 0;
    }

    @Override
    public List<UrlDTO> loadOpen() {
        return urlDao.selectList(new QueryWrapper<UrlDTO>()
                .eq("common", 1).ne("pid", "null"));
    }
}
