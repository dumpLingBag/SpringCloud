package com.rngay.feign.socket.dto;

import com.rngay.feign.dto.CommonDTO;

public class MessageDTO extends CommonDTO {

    private String sendUserId;

    private String receiveUserId;

    private ContentDTO content;

    public String getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(String receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public ContentDTO getContent() {
        return content;
    }

    public void setContent(ContentDTO content) {
        this.content = content;
    }
}
