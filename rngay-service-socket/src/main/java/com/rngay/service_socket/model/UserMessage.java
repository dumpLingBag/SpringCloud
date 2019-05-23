package com.rngay.service_socket.model;

import com.rngay.feign.socket.dto.SmsTypeEnum;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_message")
public class UserMessage {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String send;

    private String receive;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String sendReceive;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 128)
    private SmsTypeEnum smsType;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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
