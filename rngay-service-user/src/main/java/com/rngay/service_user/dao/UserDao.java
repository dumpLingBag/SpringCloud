package com.rngay.service_user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.feign.user.dto.UAUserDTO;
import com.rngay.feign.user.dto.UAUserPageListDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends BaseMapper<UAUserDTO> {

    Page<UAUserDTO> pageList(Page<UAUserDTO> page, @Param("pageList") UAUserPageListDTO pageListDTO);

}
