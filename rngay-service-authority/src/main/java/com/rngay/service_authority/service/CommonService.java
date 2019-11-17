package com.rngay.service_authority.service;

import com.rngay.feign.platform.CommonUrlDTO;
import com.rngay.feign.platform.UrlDTO;

import java.util.List;

public interface CommonService {

    /**
     * 插入或更新公共菜单
     * @Author pengcheng
     * @Date 2019/4/2
     **/
    Integer update(CommonUrlDTO urlDTO);

    /**
     * 获取选中的公共菜单
     * @Author pengcheng
     * @Date 2019/4/2
     **/
    List<UrlDTO> loadOpen();

}
