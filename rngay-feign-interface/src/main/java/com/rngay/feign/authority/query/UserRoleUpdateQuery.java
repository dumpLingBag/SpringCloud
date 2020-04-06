package com.rngay.feign.authority.query;

import com.rngay.feign.dto.CommonDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class UserRoleUpdateQuery extends CommonDTO {

    @Size(min = 1, message = "最少选择一个角色")
    private List<Long> roleIds;
    @NotNull(message = "缺少用户id")
    private Long userId;

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
