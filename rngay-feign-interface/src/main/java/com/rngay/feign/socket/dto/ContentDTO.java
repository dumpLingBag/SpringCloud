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
    private String text;

    private String fmTo;

    @NotNull(message = "时间为空")
    private Date time;

    @NotNull(message = "消息类型为空")
    @Enumerated(value = EnumType.STRING)
    private SmsTypeEnum smsType;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFmTo() {
        return fmTo;
    }

    public void setFmTo(String fmTo) {
        this.fmTo = fmTo;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public SmsTypeEnum getSmsType() {
        return smsType;
    }

    public void setSmsType(SmsTypeEnum smsType) {
        this.smsType = smsType;
    }
}
