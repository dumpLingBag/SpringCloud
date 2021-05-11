package com.rngay.feign.authority.vo;

import com.rngay.feign.authority.MenuDTO;

import java.util.List;

public class RoleMenuVo {

    private List<Long> roleMenu;
    private List<MenuDTO> menu;
    private List<Long> checked;

    public List<Long> getRoleMenu() {
        return roleMenu;
    }

    public void setRoleMenu(List<Long> roleMenu) {
        this.roleMenu = roleMenu;
    }

    public List<MenuDTO> getMenu() {
        return menu;
    }

    public void setMenu(List<MenuDTO> menu) {
        this.menu = menu;
    }

    public List<Long> getChecked() {
        return checked;
    }

    public void setChecked(List<Long> checked) {
        this.checked = checked;
    }
}
