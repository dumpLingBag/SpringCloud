package com.rngay.service_authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rngay.feign.authority.*;
import com.rngay.feign.authority.query.RoleIdListQuery;
import com.rngay.feign.user.dto.UaUserDTO;
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
public class RoleServiceImpl extends ServiceImpl<RoleDao, RoleDTO> implements RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private OrgRoleDao orgRoleDao;
    @Autowired
    private RoleMenuDao roleMenuDao;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private DeptRoleDao deptRoleDao;

    @Override
    public List<RoleDTO> list(Long orgId) {
        QueryWrapper<RoleDTO> wrapper = new QueryWrapper<>();
        wrapper.eq("org_id", orgId).eq("del_flag", 1).orderByAsc("sort");
        return roleList(roleDao.selectList(wrapper));
    }

    @Override
    public List<RoleDTO> loadUserRole(UaUserDTO userDTO) {
        return roleDao.loadUserRole(userDTO);
    }

    @Override
    public List<RoleMenuAllDTO> loadAllRole() {
        return roleDao.loadAllRole();
    }

    @Override
    public List<RoleMenuAllDTO> loadRoleByUrl(String url) {
        return roleDao.loadRoleByUrl(url);
    }

    @Override
    public List<RoleDTO> listRole(Long orgId) {
        QueryWrapper<RoleDTO> wrapper = new QueryWrapper<>();
        wrapper.eq("org_id", orgId).eq("enabled", 1).eq("del_flag", 1).orderByAsc("sort");
        return roleList(roleDao.selectList(wrapper));
    }

    @Override
    public List<RoleDTO> listByPid(Long orgId) {
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
    public Integer delete(RoleIdListQuery listDTO) {
        if (listDTO.getRoleIdList().size() > 1) {
            roleDao.deleteBatchIds(listDTO.getRoleIdList());
            orgRoleDao.delete(new QueryWrapper<OrgRoleDTO>().in("role_id", listDTO.getRoleIdList()));
            roleMenuDao.delete(new QueryWrapper<RoleMenuDTO>().in("role_id", listDTO.getRoleIdList()));
            userRoleDao.delete(new QueryWrapper<UserRoleDTO>().in("role_id", listDTO.getRoleIdList()));
            deptRoleDao.delete(new QueryWrapper<DepartmentRoleDTO>().in("role_id", listDTO.getRoleIdList()));
        }
        RoleDTO role = roleDao.selectById(listDTO.getRoleIdList().get(0));
        if (role != null) {
            roleDao.deleteById(role.getId());
            orgRoleDao.delete(new QueryWrapper<OrgRoleDTO>().eq("role_id", role.getId()));
            roleMenuDao.delete(new QueryWrapper<RoleMenuDTO>().eq("role_id", role.getId()));
            userRoleDao.delete(new QueryWrapper<UserRoleDTO>().eq("role_id", role.getId()));
            deptRoleDao.delete(new QueryWrapper<DepartmentRoleDTO>().eq("role_id", role.getId()));
            if (role.getPid() != 0) {
                List<RoleDTO> roles = roleDao.selectList(new QueryWrapper<RoleDTO>()
                .eq("pid", role.getPid()).gt("sort", role.getSort()));
                if (roles != null && !roles.isEmpty()) {
                    roleDao.updateSort(roles);
                }
            }
        }
        return null;
    }

    @Override
    public Integer updateInList(RoleInListDTO roleInList) {
        return roleDao.updateInList(roleInList);
    }

    private List<RoleDTO> roleList(List<RoleDTO> roles) {
        List<RoleDTO> arrList = new ArrayList<>();
        for (RoleDTO role : roles) {
            if (role.getPid() == null || role.getPid() == 0) {
                arrList.add(arrToRole(roles, role));
            }
        }
        arrList.sort(Comparator.comparing(RoleDTO::getSort));
        return arrList;
    }

    private RoleDTO arrToRole(List<RoleDTO> roles, RoleDTO role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        roleDTO.setLabel(role.getName());
        roleDTO.setAuthName(role.getAuthName());
        roleDTO.setEnabled(role.getEnabled());
        roleDTO.setCreateTime(role.getCreateTime());
        roleDTO.setOrgId(role.getOrgId());
        roleDTO.setPid(role.getPid());
        roleDTO.setSort(role.getSort());
        List<RoleDTO> roleDTOS = roleListChildren(roles, role);
        if (roleDTOS != null && !roleDTOS.isEmpty()) {
            roleDTO.setChildren(roleDTOS);
        }
        return roleDTO;
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
