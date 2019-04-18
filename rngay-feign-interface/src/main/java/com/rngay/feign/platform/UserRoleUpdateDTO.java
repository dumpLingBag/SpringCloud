package com.rngay.feign.platform;

import com.rngay.feign.dto.CommonDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class UserRoleUpdateDTO extends CommonDTO {

    @NotNull(message = "用户ID为空")
    private Integer userId;
    @Size(min = 1, message = "最少选择一个角色")
    private List<UserRoleDTO> roleId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public List<UserRoleDTO> getRoleId() {
        return roleId;
    }

    public void setRoleId(List<UserRoleDTO> roleId) {
        this.roleId = roleId;
    }

}
