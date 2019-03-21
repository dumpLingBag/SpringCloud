package com.rngay.feign.platform;

import com.rngay.feign.dto.CommonDTO;

import javax.persistence.Table;
import java.util.List;

@Table(name = "menu_url")
public class UpdateUrlDTO extends CommonDTO {

    private List<MenuUrlDTO> menuUrl;
    private Integer menuId;

    public List<MenuUrlDTO> getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(List<MenuUrlDTO> menuUrl) {
        this.menuUrl = menuUrl;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}
