package com.rngay.feign.platform;

import com.rngay.feign.dto.CommonDTO;

import java.util.List;

public class UpdateRoleMenuDTO extends CommonDTO {

    private Integer roleId;
    private List<RoleMenuDTO> roleMenu;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<RoleMenuDTO> getRoleMenu() {
        return roleMenu;
    }

    public void setRoleMenu(List<RoleMenuDTO> roleMenu) {
        this.roleMenu = roleMenu;
    }

}
