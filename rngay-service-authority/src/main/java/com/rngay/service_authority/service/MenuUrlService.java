package com.rngay.service_authority.service;

import com.rngay.feign.platform.MenuUrlDTO;
import com.rngay.feign.platform.UpdateUrlDTO;
import com.rngay.feign.platform.UrlDTO;

import java.util.List;

public interface MenuUrlService {

    /**
     * 加载所有 url 地址
     * @Author pengcheng
     * @Date 2019/3/30
     **/
    List<UrlDTO> load();

    /**
     * 根据 menu_id 加载关联的 url 地址
     * @Author pengcheng
     * @Date 2019/3/30
     **/
    List<MenuUrlDTO> loadUrl(Integer id);

    /**
     * 根据 menuId 插入或更新所选菜单的 url 地址
     * @Author pengcheng
     * @Date 2019/3/30
     **/
    Integer update(UpdateUrlDTO updateUrlDTO);

}
