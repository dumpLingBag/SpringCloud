package com.rngay.service_authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rngay.feign.authority.DictDataDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DictDataDao extends BaseMapper<DictDataDTO> {

    void deleteDict(@Param("dictType") String dictType);

}
