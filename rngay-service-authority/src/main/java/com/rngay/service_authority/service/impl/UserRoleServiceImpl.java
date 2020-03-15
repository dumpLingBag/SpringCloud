package com.rngay.service_authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.*;
import com.rngay.feign.user.dto.UaUserDTO;
import com.rngay.feign.user.dto.UaUserPageListDTO;
import com.rngay.feign.user.service.PFUserService;
import com.rngay.service_authority.dao.OrgRoleDao;
import com.rngay.service_authority.dao.UrlDao;
import com.rngay.service_authority.dao.UserRoleDao;
import com.rngay.service_authority.service.UserRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRoleDTO> implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private OrgRoleDao orgRoleDao;
    @Autowired
    private UrlDao urlDao;
    @Autowired
    private PFUserService pfUserService;

    @Override
    public List<UserRoleDTO> load(Long userId) {
        return userRoleDao.selectList(new QueryWrapper<UserRoleDTO>()
                .eq("checked", 1).eq("user_id", userId));
    }

    @Override
    public Boolean update(UserRoleUpdateDTO updateDTO) {
        List<UserRoleDTO> list = new ArrayList<>();
        for (Long userId : updateDTO.getUserIds()) {
            for (Long roleId : updateDTO.getRoleIds()) {
                UserRoleDTO userRoleDTO = new UserRoleDTO();
                userRoleDTO.setUserId(userId);
                userRoleDTO.setRoleId(roleId);
                userRoleDTO.setChecked(1);
                list.add(userRoleDTO);
            }
        }
        if (!list.isEmpty()) {
            return saveOrUpdateBatch(list);
        }
        return false;
        /*if (!updateDTO.getRoleId().isEmpty() && updateDTO.getUserId() != null) {
            int i = 0;
            List<UserRoleDTO> list = new ArrayList<>();
            for (UserRoleDTO role : updateDTO.getRoleId()) {
                int update = userRoleDao.update(role, new QueryWrapper<UserRoleDTO>()
                        .eq("user_id", updateDTO.getUserId())
                        .eq("role_id", role.getRoleId()));
                if (update <= 0) {
                    role.setUserId(updateDTO.getUserId());
                    list.add(role);
                } else {
                    i += update;
                }
            }
            if (!list.isEmpty()) {
                saveBatch(list);
            }
            return i + list.size();
        }
        return 0;*/
    }

    @Override
    public List<String> loadUrlByOrgId(Long orgId) {
        if (orgId != null && orgId > 0) {
            List<OrgRoleDTO> orgRoles = orgRoleDao.selectList(new QueryWrapper<OrgRoleDTO>()
                    .eq("checked", 1).eq("org_id", orgId));
            if (orgRoles == null || orgRoles.isEmpty()) return new ArrayList<>();
            return urlDao.loadUrlByOrgId(orgRoles);
        }
        return urlDao.loadUrlByList();
    }

    @Override
    public List<String> loadUrlByUserId(Long orgId, Long userId) {
        List<UserRoleDTO> roleId = userRoleDao.getRoleId(userId);
        if (roleId == null || roleId.isEmpty()) return new ArrayList<>();
        if (orgId != null && orgId > 0) {
            List<OrgRoleDTO> orgRoles = orgRoleDao.selectList(new QueryWrapper<OrgRoleDTO>()
                    .eq("checked", 1).eq("org_id", orgId));
            if (orgRoles == null || orgRoles.isEmpty()) return new ArrayList<>();
            return urlDao.loadUrlByOrgUserId(orgRoles, roleId);
        }
        return urlDao.loadUrlByUserId(roleId);
    }

    @Override
    public Page<UaUserDTO> loadUserByRoleId(UserRoleParamDTO userRole) {
        Page<UserRoleDTO> dtoPage = new Page<>(userRole.getCurrentPage(), userRole.getPageSize());
        if (userRole.getRoleId() != null) {
            Page<UserRoleDTO> roleDTOPage = userRoleDao.loadPageUserByRoleId(dtoPage, userRole.getRoleId());
            if (roleDTOPage.getTotal() <= 0) {
                return new Page<UaUserDTO>(userRole.getCurrentPage(), userRole.getPageSize()).setRecords(new ArrayList<>());
            }
            Result<List<UaUserDTO>> result = pfUserService.loadByUserIds(roleDTOPage.getRecords());
            if (result.getData() != null && !result.getData().isEmpty()) {
                List<UaUserDTO> data = result.getData();
                for (UaUserDTO uaUserDTO : data) {
                    for (UserRoleDTO roleDTO : roleDTOPage.getRecords()) {
                        if (!uaUserDTO.getId().equals(roleDTO.getUserId())) {
                            continue;
                        }
                        uaUserDTO.getRoles().add(roleDTO);
                    }
                }
            }
            return new Page<UaUserDTO>(userRole.getCurrentPage(), userRole.getPageSize(), roleDTOPage.getTotal()).setRecords(result.getData());
        } else {
            UaUserPageListDTO uaUserPage = new UaUserPageListDTO();
            BeanUtils.copyProperties(userRole, uaUserPage);
            Result<Page<UaUserDTO>> pageResult = pfUserService.pageList(uaUserPage);
            Page<UaUserDTO> data = pageResult.getData();
            if (data != null && !data.getRecords().isEmpty()) {
                List<UserRoleDTO> roleDTOS = userRoleDao.loadRoleByUserId(data.getRecords());
                for (UaUserDTO uaUserDTO : data.getRecords()) {
                    for (UserRoleDTO userRoleDTO : roleDTOS) {
                        if (uaUserDTO.getId().equals(userRoleDTO.getUserId())) {
                            uaUserDTO.getRoles().add(userRoleDTO);
                        }
                    }
                }
                return new Page<UaUserDTO>(userRole.getCurrentPage(), userRole.getPageSize(), data.getTotal()).setRecords(data.getRecords());
            }
            return new Page<>(userRole.getCurrentPage(), userRole.getPageSize());
        }
    }
}
