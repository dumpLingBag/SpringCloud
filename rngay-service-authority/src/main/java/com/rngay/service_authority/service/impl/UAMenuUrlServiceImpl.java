package com.rngay.service_authority.service.impl;

import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.feign.platform.MenuUrlDTO;
import com.rngay.feign.platform.UpdateUrlDTO;
import com.rngay.service_authority.model.UAMenuUrl;
import com.rngay.service_authority.service.UAMenuUrlService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UAMenuUrlServiceImpl implements UAMenuUrlService {

    @Resource
    private Dao dao;

    @Override
    public List<Map<String, Object>> load() {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Map<String, Object>> urls = dao.query("ua_url", Cnd.where("pid", "=", "null"));
        for (Map<String, Object> url : urls) {
            Map<String, Object> urlChildren = new HashMap<>();
            urlChildren.put("id", url.get("id"));
            urlChildren.put("common", url.get("common"));
            urlChildren.put("pid", url.get("pid"));
            urlChildren.put("url", url.get("url"));
            urlChildren.put("name", url.get("name"));
            urlChildren.put("children", getChildren(String.valueOf(url.get("id"))));
            list.add(urlChildren);
        }
        return list;
    }

    @Override
    public List<UAMenuUrl> loadUrl(Integer id) {
        return dao.query(UAMenuUrl.class, Cnd.where("menu_id","=",id).and("checked","=",1));
    }

    @Transactional
    @Override
    public Integer update(UpdateUrlDTO updateUrlDTO) {
        if (updateUrlDTO.getMenuId() != null && !updateUrlDTO.getMenuUrl().isEmpty()) {
            int i = 0;
            List<MenuUrlDTO> list = new ArrayList<>();
            for (MenuUrlDTO url : updateUrlDTO.getMenuUrl()) {
                int update = dao.update(url, Cnd.where("url_id", "=", url.getUrlId()).and("menu_id", "=", updateUrlDTO.getMenuId()));
                if (update <= 0) {
                    url.setMenuId(updateUrlDTO.getMenuId());
                    list.add(url);
                } else {
                    i+= update;
                }
            }
            if (!list.isEmpty()) {
                int[] ints = dao.batchInsert(list);
                i = i + ints.length;
            }
            return i;
        }
        return 0;
    }

    private List<Object> getChildren(String parentId) {
        List<Object> list = new ArrayList<>();
        List<Map<String, Object>> children = dao.query("ua_url", Cnd.where("pid", "=", parentId));
        for (Map<String, Object> url : children) {
            if (parentId.equals(url.get("pid"))) {
                list.add(children(url));
            }
        }
        return list;
    }

    private Map<String, Object> children(Map<String, Object> url) {
        Map<String, Object> urlChildren = new HashMap<>();
        urlChildren.put("id", url.get("id"));
        urlChildren.put("common", url.get("common"));
        urlChildren.put("pid", url.get("pid"));
        urlChildren.put("url", url.get("url"));
        urlChildren.put("name", url.get("name"));
        urlChildren.put("children", getChildren(String.valueOf(url.get("id"))));
        return urlChildren;
    }

}
