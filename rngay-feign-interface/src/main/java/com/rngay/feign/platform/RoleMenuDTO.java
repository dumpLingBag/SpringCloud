package com.rngay.feign.platform;

import com.rngay.feign.dto.CommonDTO;

import javax.persistence.Table;

@Table(name = "ua_role_menu")
public class RoleMenuDTO extends CommonDTO {

    private Integer checked;
    private Integer menuId;
    private Integer roleId;

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}
