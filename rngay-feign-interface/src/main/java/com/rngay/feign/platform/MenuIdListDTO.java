package com.rngay.feign.platform;

import com.rngay.feign.dto.CommonDTO;

import java.util.List;

public class MenuIdListDTO extends CommonDTO {

    private List<Integer> menuIdList;

    public List<Integer> getMenuIdList() {
        return menuIdList;
    }

    public void setMenuIdList(List<Integer> menuIdList) {
        this.menuIdList = menuIdList;
    }
}
