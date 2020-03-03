package com.rngay.service_authority.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rngay.feign.authority.DictTypeDTO;
import com.rngay.service_authority.dao.DictTypeDao;
import com.rngay.service_authority.service.DictTypeService;
import org.springframework.stereotype.Service;

@Service
public class DictTypeServiceImpl extends ServiceImpl<DictTypeDao, DictTypeDTO> implements DictTypeService {
}
