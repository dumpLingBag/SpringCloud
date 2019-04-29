package com.rngay.feign.socket.dto;

import com.rngay.feign.dto.PageQueryDTO;

public class PageMessageDTO extends PageQueryDTO {

    private String supplierId;

    private String memberId;

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
