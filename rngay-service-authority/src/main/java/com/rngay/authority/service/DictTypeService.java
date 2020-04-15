package com.rngay.authority.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.rngay.feign.authority.DictTypeDTO;
import com.rngay.feign.authority.query.DictTypesQuery;
import com.rngay.feign.authority.query.DictTypeQuery;

public interface DictTypeService extends IService<DictTypeDTO> {

    Page<DictTypeDTO> page(DictTypeQuery dictTypeQuery);

    Boolean deleteDictType(DictTypesQuery idsQuery);

    DictTypeDTO getDictType(String dictType);

}
