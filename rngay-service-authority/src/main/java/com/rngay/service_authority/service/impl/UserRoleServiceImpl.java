package com.rngay.service_authority.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rngay.feign.authority.OrgRoleDTO;
import com.rngay.feign.authority.UserRoleDTO;
import com.rngay.feign.authority.UserRoleUpdateDTO;
import com.rngay.service_authority.dao.OrgRoleDao;
import com.rngay.service_authority.dao.UrlDao;
import com.rngay.service_authority.dao.UserRoleDao;
import com.rngay.service_authority.service.UserRoleService;
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

    @Override
    public List<UserRoleDTO> load(Long userId) {
        return userRoleDao.selectList(new QueryWrapper<UserRoleDTO>()
                .eq("checked", 1).eq("user_id", userId));
    }

    @Override
    public Integer update(UserRoleUpdateDTO updateDTO) {
        if (!updateDTO.getRoleId().isEmpty() && updateDTO.getUserId() != null) {
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
        return 0;
    }

    @Override
    public List<String> loadUrlByOrgId(Integer orgId) {
        if (orgId != null && orgId > 0) {
            List<OrgRoleDTO> orgRoles = orgRoleDao.selectList(new QueryWrapper<OrgRoleDTO>()
                    .eq("checked", 1).eq("org_id", orgId));
            if (orgRoles == null || orgRoles.isEmpty()) return new ArrayList<>();
            return urlDao.loadUrlByOrgId(orgRoles);
        }
        return urlDao.loadUrlByList();
    }

    @Override
    public List<String> loadUrlByUserId(Integer orgId, Long userId) {
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
}
