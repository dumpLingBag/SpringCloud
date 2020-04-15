package com.rngay.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rngay.feign.authority.DictTypeDTO;
import com.rngay.feign.authority.query.DictTypesQuery;
import com.rngay.feign.authority.query.DictTypeQuery;
import com.rngay.authority.dao.DictDataDao;
import com.rngay.authority.dao.DictTypeDao;
import com.rngay.authority.service.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeDao, DictTypeDTO> implements DictTypeService {

    @Autowired
    private DictTypeDao dictTypeDao;
    @Autowired
    private DictDataDao dictDataDao;

    @Override
    public Page<DictTypeDTO> page(DictTypeQuery dictTypeQuery) {
        Page<DictTypeDTO> page = new Page<>(dictTypeQuery.getCurrentPage(), dictTypeQuery.getPageSize());
        return dictTypeDao.page(page, dictTypeQuery);
    }

    @Transactional
    @Override
    public Boolean deleteDictType(DictTypesQuery idsQuery) {
        if (!idsQuery.getDictTypes().isEmpty()) {
            dictTypeDao.deleteBatchDictType(idsQuery.getDictTypes());
            dictDataDao.deleteBatchDictData(idsQuery.getDictTypes());
            return true;
        }
        return false;
    }

    @Override
    public DictTypeDTO getDictType(String dictType) {
        QueryWrapper<DictTypeDTO> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_type", dictType);
        wrapper.eq("del_flag", 1);
        return dictTypeDao.selectOne(wrapper);
    }
}
