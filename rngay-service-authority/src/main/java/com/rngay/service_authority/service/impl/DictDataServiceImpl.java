package com.rngay.service_authority.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rngay.feign.authority.DictDataDTO;
import com.rngay.service_authority.dao.DictDataDao;
import com.rngay.service_authority.service.DictDataService;
import org.springframework.stereotype.Service;

@Service
public class DictDataServiceImpl extends ServiceImpl<DictDataDao, DictDataDTO> implements DictDataService {

}
