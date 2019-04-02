package com.rngay.service_authority.service.impl;

import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.feign.platform.RoleIdListDTO;
import com.rngay.service_authority.model.*;
import com.rngay.service_authority.service.UARoleService;
import com.rngay.service_authority.util.SortUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public List<Map<String, Object>> loadByPid(Integer orgId) {
        List<Map<String, Object>> maps = new ArrayList<>();
        List<UARole> rolePid = dao.query(UARole.class, Cnd.where("org_id", "=", orgId).and("pid", "=", 0));
        if (rolePid != null && !rolePid.isEmpty()) {
            rolePid.forEach(k -> {
                List<UARole> role = dao.query(UARole.class, Cnd.where("org_id", "=", orgId).and("pid","=", k.getId()));
                Map<String, Object> map = new HashMap<>();
                map.put("id", k.getId());
                map.put("name", k.getName());
                map.put("children", role);
                maps.add(map);
            });
        }
        return maps;
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

    @Transactional
    @Override
    public Integer delete(RoleIdListDTO listDTO) {
        if (listDTO.getRoleIdList().size() > 1) {
            dao.delete(UARole.class, Cnd.where("id","in", listDTO.getRoleIdList()));
            dao.delete(UAOrgRole.class, Cnd.where("role_id","in", listDTO.getRoleIdList()));
            dao.delete(UARoleMenu.class, Cnd.where("role_id","in", listDTO.getRoleIdList()));
            dao.delete(UAUserRole.class, Cnd.where("role_id","in", listDTO.getRoleIdList()));
            dao.delete(UADepartmentRole.class, Cnd.where("role_id","in", listDTO.getRoleIdList()));
        }
        UARole role = dao.findById(UARole.class, listDTO.getRoleIdList().get(0));
        if (role != null) {
            dao.delete(UARole.class, Cnd.where("id","=", role.getId()));
            dao.delete(UAOrgRole.class, Cnd.where("role_id","=", role.getId()));
            dao.delete(UARoleMenu.class, Cnd.where("role_id","=", role.getId()));
            dao.delete(UAUserRole.class, Cnd.where("role_id","=", role.getId()));
            dao.delete(UADepartmentRole.class, Cnd.where("role_id","=", role.getId()));
            if (!role.getPid().equals(0)) {
                List<UARole> roles = dao.query(UARole.class, Cnd.where("pid", "=", role.getPid()).and("sort", ">", role.getSort()));
                if (roles != null && !roles.isEmpty()) {
                    StringBuilder sort = new StringBuilder();
                    roles.forEach(key -> sort.append(key.getId()).append(","));
                    sort.deleteCharAt(sort.length() - 1);
                    dao.update("UPDATE ua_role SET sort = sort - 1 WHERE id IN ("+ sort.toString() +")");
                }
                return 0;
            }
            return 1;
        }
        return 0;
    }

}
