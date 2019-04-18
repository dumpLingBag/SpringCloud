package com.rngay.feign.platform;

import com.rngay.feign.dto.CommonDTO;

import javax.persistence.Table;

@Table(name = "ua_menu_url")
public class MenuUrlDTO extends CommonDTO {

    private Integer checked;
    private String urlId;
    private Integer menuId;

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }
}
