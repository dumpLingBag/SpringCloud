package com.rngay.service_authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rngay.feign.platform.MenuUrlDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuUrlDao extends BaseMapper<MenuUrlDTO> {
}