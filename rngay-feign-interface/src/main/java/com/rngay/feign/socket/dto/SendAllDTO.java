package com.rngay.feign.socket.dto;

import com.rngay.feign.dto.CommonDTO;

import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Table(name = "user_all_message")
public class SendAllDTO extends CommonDTO {

    private Integer id;

    private Integer sendUserId;

    private List<Integer> receiveUserId;

    private String content;

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

    public List<Integer> getReceiveUserId() {
        return receiveUserId;
    }

    public void setReceiveUserId(List<Integer> receiveUserId) {
        this.receiveUserId = receiveUserId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
