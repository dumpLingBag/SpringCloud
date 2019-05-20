package com.rngay.feign.socket.dto;

public enum SmsTypeEnum {
    TEXT("文本", 0), IMAGE("图片", 1), VOICE("语音", 2);

    private String name;
    private Integer index;

    SmsTypeEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (SmsTypeEnum sms : SmsTypeEnum.values()) {
            if (sms.getIndex() == index) {
                return sms.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
