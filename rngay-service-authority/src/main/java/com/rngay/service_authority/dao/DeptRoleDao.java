package com.rngay.service_authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rngay.feign.authority.DepartmentRoleDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptRoleDao extends BaseMapper<DepartmentRoleDTO> {
}
