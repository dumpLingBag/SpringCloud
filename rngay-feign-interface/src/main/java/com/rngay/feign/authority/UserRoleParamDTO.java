package com.rngay.feign.authority;

import com.rngay.feign.dto.CommonDTO;

public class UserRoleParamDTO extends CommonDTO {

    private Long roleIds;
    private Long userIds;

    public Long getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long roleIds) {
        this.roleIds = roleIds;
    }

    public Long getUserIds() {
        return userIds;
    }

    public void setUserIds(Long userIds) {
        this.userIds = userIds;
    }
}
