package com.rngay.service_socket.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_message")
public class UserMessage {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    private String fm;

    private String to;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String fmTo;

    private String smsType;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getSmsType() {
        return smsType;
    }

    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
