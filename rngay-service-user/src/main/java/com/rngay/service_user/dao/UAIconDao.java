package com.rngay.service_user.dao;

import com.rngay.feign.user.dto.UAIconDTO;

import java.util.List;

public interface UAIconDao {

    /**
     * 加载 icon 图标
     * @Author: pengcheng
     * @Date: 2018/12/22
     */
    List<UAIconDTO> loadIcon();

}
