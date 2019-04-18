package com.rngay.service_user.service;

import com.rngay.feign.user.dto.UAIconDTO;

import java.util.List;

public interface UAIconService {

    /**
    * 加载 icon 图标
    * @Author: pengcheng
    * @Date: 2018/12/22
    */
    List<UAIconDTO> loadIcon();

}
