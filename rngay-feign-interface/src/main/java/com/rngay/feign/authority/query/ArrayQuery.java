package com.rngay.feign.authority.query;

import com.rngay.feign.dto.CommonDTO;

import javax.validation.constraints.Size;
import java.util.List;

public class ArrayQuery extends CommonDTO {

    @Size(min = 1, message = "至少包含一项")
    private List<Long> ids;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
