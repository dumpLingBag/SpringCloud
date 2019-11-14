package com.rngay.service_authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rngay.feign.platform.MenuUrlDTO;
import com.rngay.feign.platform.UpdateUrlDTO;
import com.rngay.feign.platform.UrlDTO;
import com.rngay.service_authority.dao.MenuUrlDao;
import com.rngay.service_authority.dao.UrlDao;
import com.rngay.service_authority.service.MenuUrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class MenuUrlServiceImpl implements MenuUrlService {

    @Autowired
    private UrlDao urlDao;
    @Autowired
    private MenuUrlDao menuUrlDao;

    @Override
    public List<UrlDTO> load() {
        List<UrlDTO> list = new ArrayList<>();
        List<UrlDTO> urls = urlDao.selectList(new QueryWrapper<UrlDTO>().isNull("pid"));
        for (UrlDTO url : urls) {
            UrlDTO uaUrl = new UrlDTO();
            children(uaUrl, url);
            list.add(uaUrl);
        }
        return list;
    }

    @Override
    public List<MenuUrlDTO> loadUrl(Integer id) {
        return menuUrlDao.selectList(new QueryWrapper<MenuUrlDTO>().eq("menu_id", id).eq("checked", 1));
    }

    @Override
    public Integer update(UpdateUrlDTO updateUrlDTO) {
//        if (updateUrlDTO.getMenuId() != null && !updateUrlDTO.getMenuUrl().isEmpty()) {
//            int i = 0;
//            List<MenuUrlDTO> list = new ArrayList<>();
//            for (MenuUrlDTO url : updateUrlDTO.getMenuUrl()) {
//                urlDao.update(url,new QueryWrapper<>());
//            }
//        }
        return null;
    }

    private List<UrlDTO> getChildren(String parentId) {
        List<UrlDTO> list = new ArrayList<>();
        List<UrlDTO> urls = urlDao.selectList(new QueryWrapper<UrlDTO>().eq("pid", parentId));
        for (UrlDTO url : urls) {
            if (parentId.equals(url.getPid())) {
                UrlDTO uaUrl = new UrlDTO();
                children(uaUrl, url);
                list.add(uaUrl);
            }
        }
        return list;
    }

    private void children(UrlDTO uaUrl, UrlDTO url) {
        uaUrl.setId(url.getId());
        uaUrl.setCommon(url.getCommon());
        uaUrl.setPid(url.getPid());
        uaUrl.setUrl(url.getUrl());
        uaUrl.setName(url.getName());
        uaUrl.setChildren(getChildren(url.getId()));
    }

}
