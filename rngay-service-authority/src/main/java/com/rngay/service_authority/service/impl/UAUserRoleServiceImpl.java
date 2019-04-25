package com.rngay.service_authority.service.impl;

import com.rngay.common.jpa.dao.Cnd;
import com.rngay.common.jpa.dao.Dao;
import com.rngay.feign.platform.UserRoleDTO;
import com.rngay.feign.platform.UserRoleUpdateDTO;
import com.rngay.service_authority.model.UAUserRole;
import com.rngay.service_authority.service.UAUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UAUserRoleServiceImpl implements UAUserRoleService {

    @Autowired
    private Dao dao;

    @Override
    public List<UAUserRole> load(Integer userId) {
        return dao.query(UAUserRole.class, Cnd.where("checked","=",1).and("user_id","=", userId));
    }

    @Transactional
    @Override
    public Integer update(UserRoleUpdateDTO updateDTO) {
        if (!updateDTO.getRoleId().isEmpty() && updateDTO.getUserId() != null) {
            int i = 0;
            List<UserRoleDTO> list = new ArrayList<>();
            for (UserRoleDTO role : updateDTO.getRoleId()) {
                int update = dao.update(role, Cnd.where("user_id", "=", updateDTO.getUserId()).and("role_id", "=", role.getRoleId()));
                if (update <= 0) {
                    role.setUserId(updateDTO.getUserId());
                    list.add(role);
                } else {
                    i += update;
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

}
