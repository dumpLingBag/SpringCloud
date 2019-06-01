package com.rngay.service_socket.model;

import com.rngay.feign.socket.dto.SmsTypeEnum;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_message_info")
public class UserMessageInfo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 32)
    private String userInfoId;

    private Integer fromUserId;

    private Integer toUserId;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(columnDefinition = "tinyint default 0")
    private Integer smsStatus;

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

    public String getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(String userInfoId) {
        this.userInfoId = userInfoId;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(Integer smsStatus) {
        this.smsStatus = smsStatus;
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
