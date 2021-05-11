package com.rngay.feign.authority.query;

import com.rngay.feign.dto.BaseDTO;

public class DictDataQuery extends BaseDTO {

    private String dictDataLabel;
    private Integer enabled;
    private String dictType;

    public String getDictDataLabel() {
        return dictDataLabel;
    }

    public void setDictDataLabel(String dictDataLabel) {
        this.dictDataLabel = dictDataLabel;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public String getDictType() {
        return dictType;
    }

    public void setDictType(String dictType) {
        this.dictType = dictType;
    }
}
