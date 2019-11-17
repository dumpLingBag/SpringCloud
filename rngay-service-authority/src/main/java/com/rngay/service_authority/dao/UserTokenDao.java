package com.rngay.service_authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rngay.feign.platform.UserTokenDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenDao extends BaseMapper<UserTokenDTO> {
}
