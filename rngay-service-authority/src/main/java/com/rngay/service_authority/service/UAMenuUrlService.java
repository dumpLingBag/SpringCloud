package com.rngay.service_authority.service;

import com.rngay.feign.platform.UpdateUrlDTO;
import com.rngay.service_authority.model.UAMenuUrl;

import java.util.List;
import java.util.Map;

public interface UAMenuUrlService {

    /**
    * 加载所有 url 地址
    * @Author pengcheng
    * @Date 2019/3/30
    **/
    List<Map<String, Object>> load();

    /**
    * 根据 menu_id 加载关联的 url 地址
    * @Author pengcheng
    * @Date 2019/3/30
    **/
    List<UAMenuUrl> loadUrl(Integer id);

    /**
    * 根据 menuId 插入或更新所选菜单的 url 地址
    * @Author pengcheng
    * @Date 2019/3/30
    **/
    Integer update(UpdateUrlDTO updateUrlDTO);

}
