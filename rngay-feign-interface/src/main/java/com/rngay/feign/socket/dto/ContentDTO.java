package com.rngay.feign.socket.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class ContentDTO {

    @NotBlank(message = "发送人为空")
    private String send;

    @NotBlank(message = "接收人为空")
    private String receive;

    @NotBlank(message = "内容为空")
    private String content;

    private String sendReceive;

    @NotNull(message = "消息类型为空")
    @Enumerated(value = EnumType.STRING)
    private SmsTypeEnum smsType;

    @NotNull(message = "时间为空")
    private Date createTime;

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String receive) {
        this.receive = receive;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendReceive() {
        return sendReceive;
    }

    public void setSendReceive(String sendReceive) {
        this.sendReceive = sendReceive;
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
