package com.rngay.feign.platform;

import com.baomidou.mybatisplus.annotation.TableName;
import com.rngay.feign.dto.CommonDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@TableName(value = "menu_url")
public class UpdateUrlDTO extends CommonDTO {

    @Size(min = 1, message = "至少选择一个URL")
    private List<MenuUrlDTO> menuUrl;
    @NotNull(message = "菜单ID为空")
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
