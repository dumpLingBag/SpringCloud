package com.rngay.authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rngay.feign.authority.*;
import com.rngay.feign.authority.query.RoleIdListQuery;
import com.rngay.feign.user.dto.UaUserDTO;
import com.rngay.authority.dao.*;
import com.rngay.authority.service.RoleService;
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
    public List<RoleDTO> listUserRole(UaUserDTO userDTO) {
        return roleDao.listUserRole(userDTO);
    }

    @Override
    public List<RoleMenuAllDTO> listAllRole() {
        return roleDao.listAllRole();
    }

    @Override
    public List<RoleMenuAllDTO> listRoleByUrl(String url) {
        return roleDao.listRoleByUrl(url);
    }

    @Override
    public List<RoleDTO> listRole(Long orgId) {
        QueryWrapper<RoleDTO> wrapper = new QueryWrapper<>();
        wrapper.eq("org_id", orgId).eq("enabled", 1).eq("del_flag", 1).orderByAsc("sort");
        return roleList(roleDao.selectList(wrapper));
    }

    @Override
    public List<RoleDTO> listByPid(Long orgId) {
        QueryWrapper<RoleDTO> wrapper = new QueryWrapper<>();
        wrapper.eq("org_id", orgId).eq("del_flag", "1");
        wrapper.ne("pid", "0");
        return roleDao.selectList(wrapper);
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

    @Override
    public Integer insert(RoleDTO uaRole) {
        if (uaRole.getPid() != null && uaRole.getPid() != 1) {
            RoleDTO byId = roleDao.selectById(uaRole.getPid());
            if (byId != null) {
                byId.setPid(0L);
                roleDao.updateById(byId);
            }
        }
        return roleDao.insert(uaRole);
    }

    private List<RoleDTO> roleList(List<RoleDTO> roles) {
        List<RoleDTO> arrList = new ArrayList<>();
        for (RoleDTO role : roles) {
            if (role.getPid() == null || role.getPid() == 0 || role.getPid() == 1) {
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
