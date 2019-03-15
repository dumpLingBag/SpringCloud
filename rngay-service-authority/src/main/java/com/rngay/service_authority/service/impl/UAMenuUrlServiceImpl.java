package com.rngay.service_authority.service.impl;

import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.service_authority.model.UAMenuUrl;
import com.rngay.service_authority.service.UAMenuUrlService;
import org.springframework.stereotype.Service;

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
            urlChildren.put("children", getChildren(String.valueOf(url.get("id"))));
            list.add(urlChildren);
        }
        return list;
    }

    @Override
    public List<UAMenuUrl> loadUrl(Integer id) {
        return dao.query(UAMenuUrl.class, Cnd.where("menu_id","=",id).and("checked","=",1));
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
        urlChildren.put("children", getChildren(String.valueOf(url.get("id"))));
        return urlChildren;
    }

}
