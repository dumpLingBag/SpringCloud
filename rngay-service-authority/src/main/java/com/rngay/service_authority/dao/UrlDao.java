package com.rngay.service_authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rngay.feign.platform.UrlDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlDao extends BaseMapper<UrlDTO> {
}
