package com.rngay.service_authority.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rngay.feign.authority.MenuUrlDTO;
import com.rngay.feign.authority.UpdateUrlDTO;
import com.rngay.feign.authority.UrlDTO;

import java.util.List;

public interface MenuUrlService extends IService<MenuUrlDTO> {

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
    List<MenuUrlDTO> listUrl(Long id);

    /**
     * 根据 menuId 插入或更新所选菜单的 url 地址
     * @Author pengcheng
     * @Date 2019/3/30
     **/
    Integer update(UpdateUrlDTO updateUrlDTO);

}
