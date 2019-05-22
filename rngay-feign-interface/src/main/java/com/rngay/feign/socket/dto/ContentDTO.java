package com.rngay.feign.socket.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ContentDTO {

    @NotBlank(message = "发送人为空")
    private String fm;

    @NotBlank(message = "接收人为空")
    private String to;

    @NotBlank(message = "内容为空")
    private String content;

    private String fmTo;

    @NotNull(message = "消息类型为空")
    @Enumerated(value = EnumType.STRING)
    private SmsTypeEnum smsType;

    @NotNull(message = "时间为空")
    private Date createTime;

    public String getFm() {
        return fm;
    }

    public void setFm(String fm) {
        this.fm = fm;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFmTo() {
        return fmTo;
    }

    public void setFmTo(String fmTo) {
        this.fmTo = fmTo;
    }

    public SmsTypeEnum getSmsType() {
        return smsType;
    }

    public void setSmsType(SmsTypeEnum smsType) {
        this.smsType = smsType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
