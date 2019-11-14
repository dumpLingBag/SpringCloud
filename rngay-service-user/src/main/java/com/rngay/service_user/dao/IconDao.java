package com.rngay.service_user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rngay.feign.user.dto.UAIconDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface IconDao extends BaseMapper<UAIconDTO> {
}
