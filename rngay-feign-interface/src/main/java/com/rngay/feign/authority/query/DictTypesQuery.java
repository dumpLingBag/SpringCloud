package com.rngay.feign.authority.query;

import com.rngay.feign.dto.CommonDTO;

import javax.validation.constraints.Size;
import java.util.List;

public class DictTypesQuery extends CommonDTO {

    @Size(min = 1, message = "至少选择一条数据")
    private List<String> dictTypes;

    public List<String> getDictTypes() {
        return dictTypes;
    }

    public void setDictTypes(List<String> dictTypes) {
        this.dictTypes = dictTypes;
    }
}
