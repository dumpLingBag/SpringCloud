package com.rngay.feign.authority;

import com.rngay.feign.dto.CommonDTO;

import java.util.List;
import java.util.Set;

public class MenuAuthDTO  extends CommonDTO {

    private List<MenuDTO> menuList;
    private Set<String> authorities;

    public List<MenuDTO> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<MenuDTO> menuList) {
        this.menuList = menuList;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }
}
