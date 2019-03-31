package com.rngay.service_authority.service.impl;

import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.service_authority.model.UARole;
import com.rngay.service_authority.service.UARoleService;
import com.rngay.service_authority.util.SortUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UARoleServiceImpl implements UARoleService {

    @Resource
    private Dao dao;

    @Override
    public List<Map<String, Object>> load(Integer orgId) {
        List<Map<String, Object>> roles = dao.query("ua_role", Cnd.where("org_id", "=", orgId).asc("sort"));
        return roleList(roles);
    }

    private List<Map<String, Object>> roleList(List<Map<String, Object>> roles) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (Map<String, Object> role : roles) {
            if (role.get("pid") == null || role.get("pid").equals(0)) {
                mapList.add(mapToRole(roles, role));
            }
        }
        return SortUtil.mapSort(mapList);
    }

    private List<Map<String, Object>> roleListChildren(List<Map<String, Object>> roles, Map<String, Object> role) {
        List<Map<String, Object>> children = new ArrayList<>();
        for (Map<String, Object> pid : roles) {
            if (pid.get("pid").equals(role.get("id"))) {
                children.add(mapToRole(roles, pid));
            }
        }
        return SortUtil.mapSort(children);
    }

    private Map<String, Object> mapToRole(List<Map<String, Object>> roles, Map<String, Object> role) {
        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("id", role.get("id"));
        roleMap.put("name", role.get("name"));
        roleMap.put("orgId", role.get("org_id"));
        roleMap.put("pid", role.get("pid"));
        roleMap.put("sort", role.get("sort"));
        roleMap.put("children", roleListChildren(roles, role));
        return roleMap;
    }

    @Override
    public Integer save(UARole uaRole) {
        return dao.insert(uaRole);
    }

    @Override
    public Integer update(UARole uaRole) {
        return dao.update(uaRole);
    }

}
