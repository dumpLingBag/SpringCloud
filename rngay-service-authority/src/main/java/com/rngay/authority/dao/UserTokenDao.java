package com.rngay.authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rngay.feign.authority.UserTokenDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenDao extends BaseMapper<UserTokenDTO> {
}
