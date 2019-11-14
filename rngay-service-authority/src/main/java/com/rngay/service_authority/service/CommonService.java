package com.rngay.service_authority.service;

import com.rngay.feign.platform.CommonUrlDTO;
import com.rngay.service_authority.model.UAUrl;

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
    List<UAUrl> loadOpen();

}
