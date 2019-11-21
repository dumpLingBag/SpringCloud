package com.rngay.feign.authority;

import com.rngay.feign.dto.CommonDTO;

import java.util.List;

public class RoleIdListDTO extends CommonDTO {

    private List<Long> roleIdList;

    public List<Long> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        this.roleIdList = roleIdList;
    }
}
