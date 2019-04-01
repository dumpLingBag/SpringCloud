package com.rngay.feign.platform;

import com.rngay.feign.dto.CommonDTO;

import javax.validation.constraints.NotNull;
import java.util.List;

public class UserRoleUpdateDTO extends CommonDTO {

    @NotNull(message = "用户ID为空")
    private Integer userId;
    @NotNull(message = "角色ID为空")
    private List<Integer> roleId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<Integer> getRoleId() {
        return roleId;
    }

    public void setRoleId(List<Integer> roleId) {
        this.roleId = roleId;
    }
}
