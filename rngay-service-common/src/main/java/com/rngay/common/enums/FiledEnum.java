package com.rngay.common.enums;

public enum FiledEnum {
    MENU_TYPE_VO(1, "menu_type_vo"),
    MENU_TYPE_NOT_VO(2, "menu_type_not_vo"),
    CHECKED(100, "checked"),
    ACCESS_TOKEN(101, "access_token"),
    AUTHORITIES(102, "authorities"),
    USER_INFO(103, "user_info")
    ;

    private String msg;
    private int code;

    FiledEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMessage(int code) {
        for (FiledEnum status : FiledEnum.values()) {
            if (status.code == code) {
                return status.getMsg();
            }
        }
        return null;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
