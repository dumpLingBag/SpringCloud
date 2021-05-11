package com.rngay.feign.authority;

import java.util.List;

public class MenuInListDTO {

    private Integer enabled;

    private List<Long> menuIdList;

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public List<Long> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<Long> menuIdList) {
        this.menuIdList = menuIdList;
    }

}
