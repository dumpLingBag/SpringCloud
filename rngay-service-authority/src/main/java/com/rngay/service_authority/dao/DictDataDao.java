package com.rngay.service_authority.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rngay.feign.authority.DictDataDTO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DictDataDao extends BaseMapper<DictDataDTO> {

    void deleteDictData(@Param("dictType") String dictType);

    void deleteBatchDictData(@Param("dictTypes")  List<String> dictTypes);

}
