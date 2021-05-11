package com.rngay.feign.authority;

import com.rngay.feign.dto.CommonDTO;

import java.util.List;

public class CommonUrlDTO extends CommonDTO {

    private List<UrlDTO> urlId;

    public List<UrlDTO> getUrlId() {
        return urlId;
    }

    public void setUrlId(List<UrlDTO> urlId) {
        this.urlId = urlId;
    }

}
