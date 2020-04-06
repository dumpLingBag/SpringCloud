package com.rngay.feign.authority.query;

import com.rngay.feign.dto.CommonDTO;

import javax.validation.constraints.Size;
import java.util.List;

public class UserRoleClearQuery extends CommonDTO {

    private Long roleId;
    @Size(min = 1, message = "至少选择一个用户")
    private List<Long> userIds;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }
}
