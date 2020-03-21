package com.rngay.feign.authority.query;

import com.rngay.feign.dto.CommonDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class UserRoleUpdateQuery extends CommonDTO {

    @Size(min = 1, message = "最少选择一个角色")
    private List<Long> roleIds;
    @Size(min = 1, message = "最少选择一个用户")
    private List<Long> userIds;
    private Integer type;

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
