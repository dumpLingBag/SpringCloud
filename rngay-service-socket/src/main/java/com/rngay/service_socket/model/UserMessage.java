package com.rngay.service_socket.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_message")
public class UserMessage {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private Integer sendUserId;

    private Integer receiveUserId;

    private String smsType;

    @Column(columnDefinition = "TEXT")
    private String content;

    private Integer sendReceiveUserId;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(Integer sendUserId) {
        this.sendUserId = sendUserId;
    }

    public Integer getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(Integer receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSendReceiveUserId() {
        return sendReceiveUserId;
    }

    public void setSendReceiveUserId(Integer sendReceiveUserId) {
        this.sendReceiveUserId = sendReceiveUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
