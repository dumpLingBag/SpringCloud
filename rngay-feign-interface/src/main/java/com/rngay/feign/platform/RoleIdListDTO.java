package com.rngay.feign.platform;

import com.rngay.feign.dto.CommonDTO;

import java.util.List;

public class RoleIdListDTO extends CommonDTO {

    private List<Integer> roleIdList;

    public List<Integer> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Integer> roleIdList) {
        this.roleIdList = roleIdList;
    }
}
