package com.rngay.feign.platform;

import com.rngay.feign.dto.CommonDTO;

import javax.persistence.Table;

@Table(name = "ua_user_role")
public class UserRoleDTO extends CommonDTO {

    private Integer checked;
    private Integer userId;
    private Integer roleId;

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}
