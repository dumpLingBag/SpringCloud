package com.rngay.feign.authority;

import com.rngay.feign.dto.CommonDTO;

import java.util.List;

public class RoleInListDTO extends CommonDTO {

    private Integer enabled;

    private List<Long> roleIdList;

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public List<Long> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        this.roleIdList = roleIdList;
    }
}
