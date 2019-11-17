package com.rngay.service_authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.rngay.feign.platform.*;
import com.rngay.service_authority.dao.*;
import com.rngay.service_authority.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private OrgRoleDao orgRoleDao;
    @Autowired
    private RoleMenuDao roleMenuDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private DepartmentRoleDao departmentRoleDao;

    @Override
    public List<RoleDTO> load(Integer orgId) {
        List<RoleDTO> roles = roleDao.selectList(new QueryWrapper<RoleDTO>()
                .eq("org_id", orgId).orderByAsc("sort"));
        return roleList(roles);
    }

    @Override
    public List<RoleDTO> loadByPid(Integer orgId) {
        List<RoleDTO> roles = new ArrayList<>();
        List<RoleDTO> rolePid = roleDao.selectList(new QueryWrapper<RoleDTO>()
                .eq("org_id", orgId).eq("pid", 0));
        if (rolePid != null && !rolePid.isEmpty()) {
            rolePid.forEach(k -> {
                List<RoleDTO> role = roleDao.selectList(new QueryWrapper<RoleDTO>()
                        .eq("org_id", orgId).eq("pid", k.getPid()));
                RoleDTO roleDTO = new RoleDTO();
                roleDTO.setId(k.getId());
                roleDTO.setName(k.getName());
                roleDTO.setChildren(role);
                roles.add(roleDTO);
            });
        }
        return roles;
    }

    @Override
    public Integer save(RoleDTO uaRole) {
        return roleDao.insert(uaRole);
    }

    @Override
    public Integer update(RoleDTO uaRole) {
        return roleDao.updateById(uaRole);
    }

    @Override
    public Integer delete(RoleIdListDTO listDTO) {
        if (listDTO.getRoleIdList().size() > 1) {
            roleDao.deleteBatchIds(listDTO.getRoleIdList());
            orgRoleDao.delete(new QueryWrapper<OrgRoleDTO>().in("role_id", listDTO.getRoleIdList()));
            roleMenuDao.delete(new QueryWrapper<RoleMenuDTO>().in("role_id", listDTO.getRoleIdList()));
            userRoleDao.delete(new QueryWrapper<UserRoleDTO>().in("role_id", listDTO.getRoleIdList()));
            departmentRoleDao.delete(new QueryWrapper<DepartmentRoleDTO>().in("role_id", listDTO.getRoleIdList()));
        }
        RoleDTO role = roleDao.selectById(listDTO.getRoleIdList().get(0));
        if (role != null) {
            roleDao.deleteById(role.getId());
            orgRoleDao.delete(new QueryWrapper<OrgRoleDTO>().eq("role_id", role.getId()));
            roleMenuDao.delete(new QueryWrapper<RoleMenuDTO>().eq("role_id", role.getId()));
            userRoleDao.delete(new QueryWrapper<UserRoleDTO>().eq("role_id", role.getId()));
            departmentRoleDao.delete(new QueryWrapper<DepartmentRoleDTO>().eq("role_id", role.getId()));
            if (!role.getPid().equals(0)) {
                List<RoleDTO> roles = roleDao.selectList(new QueryWrapper<RoleDTO>()
                .eq("pid", role.getPid()).gt("sort", role.getSort()));
                if (roles != null && !roles.isEmpty()) {
                    roleDao.updateSort(roles);
                }
            }
        }
        return null;
    }

    private List<RoleDTO> roleList(List<RoleDTO> roles) {
        List<RoleDTO> arrList = new ArrayList<>();
        for (RoleDTO role : roles) {
            if (role.getPid() == null || role.getPid().equals(0)) {
                arrList.add(arrToRole(roles, role));
            }
        }
        arrList.sort(Comparator.comparing(RoleDTO::getSort));
        return arrList;
    }

    private RoleDTO arrToRole(List<RoleDTO> roles, RoleDTO role) {
        RoleDTO roleArr = new RoleDTO();
        roleArr.setId(role.getId());
        roleArr.setName(role.getName());
        roleArr.setOrgId(role.getOrgId());
        roleArr.setPid(role.getPid());
        roleArr.setSort(role.getSort());
        roleArr.setChildren(roleListChildren(roles, role));
        return roleArr;
    }

    private List<RoleDTO> roleListChildren(List<RoleDTO> roles, RoleDTO role) {
        List<RoleDTO> children = new ArrayList<>();
        for (RoleDTO pid : roles) {
            if (pid.getPid().equals(role.getId())) {
                children.add(arrToRole(roles, pid));
            }
        }
        children.sort(Comparator.comparing(RoleDTO::getSort));
        return children;
    }
}
