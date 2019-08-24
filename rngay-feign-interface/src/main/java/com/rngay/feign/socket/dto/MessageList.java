package com.rngay.feign.socket.dto;

import java.util.Date;

public class MessageList {

    private String fromAvatarPath;

    private String toAvatarPath;

    private Date createTime;

    private String fromUserId;

    private Integer id;

    private String nickName;

    private Integer sort;

    private String toUserId;

    private Integer unRead;

    private Date updateTime;

    private String userInfoId;

    private String userName;

    private UserMessage userMessage;

    public String getFromAvatarPath() {
        return fromAvatarPath;
    }

    public void setFromAvatarPath(String fromAvatarPath) {
        this.fromAvatarPath = fromAvatarPath;
    }

    public String getToAvatarPath() {
        return toAvatarPath;
    }

    public void setToAvatarPath(String toAvatarPath) {
        this.toAvatarPath = toAvatarPath;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public Integer getUnRead() {
        return unRead;
    }

    public void setUnRead(Integer unRead) {
        this.unRead = unRead;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUserInfoId() {
        return userInfoId;
    }

    public void setUserInfoId(String userInfoId) {
        this.userInfoId = userInfoId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public UserMessage getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(UserMessage userMessage) {
        this.userMessage = userMessage;
    }
}
