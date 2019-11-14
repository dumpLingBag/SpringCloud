package com.rngay.service_authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rngay.feign.platform.UserRoleDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleDao extends BaseMapper<UserRoleDTO> {
}
