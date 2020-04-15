package com.rngay.user.service;

import com.rngay.feign.user.dto.UaIconDTO;

import java.util.List;

public interface IconService {

    /**
     * 加载 icon 图标
     * @Author: pengcheng
     * @Date: 2018/12/22
     */
    List<UaIconDTO> loadIcon();

}
