package com.rngay.authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rngay.feign.authority.UserFileDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFileDao extends BaseMapper<UserFileDTO> {
}
