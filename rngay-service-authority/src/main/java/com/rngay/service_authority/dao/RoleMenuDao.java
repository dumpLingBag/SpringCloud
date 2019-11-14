package com.rngay.service_authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rngay.feign.platform.RoleMenuDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMenuDao extends BaseMapper<RoleMenuDTO> {
}
