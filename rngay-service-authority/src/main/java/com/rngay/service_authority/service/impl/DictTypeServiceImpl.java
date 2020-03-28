package com.rngay.service_authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rngay.feign.authority.DictTypeDTO;
import com.rngay.feign.authority.query.DictTypeQuery;
import com.rngay.service_authority.dao.DictDataDao;
import com.rngay.service_authority.dao.DictTypeDao;
import com.rngay.service_authority.service.DictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeDao, DictTypeDTO> implements DictTypeService {

    @Autowired
    private DictTypeDao dictTypeDao;
    @Autowired
    private DictDataDao dictDataDao;

    @Override
    public Page<DictTypeDTO> pageList(DictTypeQuery dictTypeQuery) {
        Page<DictTypeDTO> page = new Page<>(dictTypeQuery.getCurrentPage(), dictTypeQuery.getPageSize());
        return dictTypeDao.pageList(page, dictTypeQuery);
    }

    @Override
    public Boolean deleteDictType(Long id) {
        DictTypeDTO dictTypeDTO = dictTypeDao.selectById(id);
        if (dictTypeDTO != null) {
            dictTypeDTO.setDelFlag(0);
            dictTypeDao.updateById(dictTypeDTO);
            dictDataDao.deleteDict(dictTypeDTO.getDictType());
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
