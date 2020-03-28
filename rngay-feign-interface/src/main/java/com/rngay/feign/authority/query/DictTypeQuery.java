package com.rngay.feign.authority.query;

import com.rngay.feign.dto.PageQueryDTO;

public class DictTypeQuery extends PageQueryDTO {

    private String dictType;
    private String dictName;
    private Integer enabled;

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }
}
