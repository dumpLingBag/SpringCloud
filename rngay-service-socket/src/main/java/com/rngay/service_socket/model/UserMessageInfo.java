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

    private String messageId;

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

    @Column(length = 20)
    private String timeInterval;

    private Float width;

    private Float height;

    @Column(columnDefinition = "tinyint default 0")
    private Integer fromIsDelete;

    @Column(columnDefinition = "tinyint default 0")
    private Integer toIsDelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
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

    public String getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(String timeInterval) {
        this.timeInterval = timeInterval;
    }

    public Float getWidth() {
        return width;
    }

    public void setWidth(Float width) {
        this.width = width;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Integer getFromIsDelete() {
        return fromIsDelete;
    }

    public void setFromIsDelete(Integer fromIsDelete) {
        this.fromIsDelete = fromIsDelete;
    }

    public Integer getToIsDelete() {
        return toIsDelete;
    }

    public void setToIsDelete(Integer toIsDelete) {
        this.toIsDelete = toIsDelete;
    }
}
