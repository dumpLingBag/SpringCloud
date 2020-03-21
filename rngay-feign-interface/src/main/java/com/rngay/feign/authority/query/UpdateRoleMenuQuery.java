package com.rngay.feign.authority.query;

import com.rngay.feign.dto.BaseDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class UpdateRoleMenuQuery extends BaseDTO {

    @NotNull(message = "角色ID为空")
    private Long roleId;
    @Size(min = 1, message = "至少选择一个菜单")
    private List<Long> menuId;
    @NotNull(message = "请求类型为空") // 0 修改 1 增加
    private Integer type;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getMenuId() {
        return menuId;
    }

    public void setMenuId(List<Long> menuId) {
        this.menuId = menuId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
