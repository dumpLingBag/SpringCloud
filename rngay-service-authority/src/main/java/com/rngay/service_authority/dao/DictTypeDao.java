package com.rngay.service_authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rngay.feign.authority.DictTypeDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface DictTypeDao extends BaseMapper<DictTypeDTO> {
}
