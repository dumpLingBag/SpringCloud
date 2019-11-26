package com.rngay.feign.authority;

import com.rngay.feign.dto.CommonDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class UpdateRoleMenuDTO extends CommonDTO {

    @NotNull(message = "角色ID为空")
    private Long roleId;
    @Size(min = 1, message = "至少选择一个菜单")
    private List<RoleMenuDTO> roleMenu;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<RoleMenuDTO> getRoleMenu() {
        return roleMenu;
    }

    public void setRoleMenu(List<RoleMenuDTO> roleMenu) {
        this.roleMenu = roleMenu;
    }

}
