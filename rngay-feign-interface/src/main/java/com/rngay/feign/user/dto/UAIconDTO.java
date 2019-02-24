package com.rngay.feign.user.dto;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "ua_icon")
public class UAIconDTO {

    @Id
    private Integer id;

    private String icon;

    private String text;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
