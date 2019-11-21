package com.rngay.feign.authority;

import com.rngay.feign.dto.CommonDTO;

import java.util.List;

public class MenuIdListDTO extends CommonDTO {

    private List<Long> menuIdList;

    public List<Long> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<Long> menuIdList) {
        this.menuIdList = menuIdList;
    }
}
