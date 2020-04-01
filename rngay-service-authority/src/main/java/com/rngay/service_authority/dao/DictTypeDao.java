package com.rngay.service_authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rngay.feign.authority.DictTypeDTO;
import com.rngay.feign.authority.query.DictTypeQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictTypeDao extends BaseMapper<DictTypeDTO> {

    Page<DictTypeDTO> page(Page<DictTypeDTO> page, @Param("pageList") DictTypeQuery dictTypeQuery);

    void deleteBatchDictType(@Param("dictTypes") List<String> dictTypes);

}
