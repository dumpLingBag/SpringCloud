package com.rngay.service_authority.security;

public enum ErrorCodeEnum {

    TOKEN_INVALID(2, "用户信息已过期，请重新登录"),
    TOKEN_OTHER_LOGIN(2, "账号在其他地方登录，请重新登录"),
    LOGIN_FAIL(1, "登录失败，请重试"),
    LOGIN_INFO_FAIL(1, "更新用户信息失败");

    private String msg;
    private int code;

    ErrorCodeEnum(int code, String message) {
        this.code = code;
        this.msg = message;
    }

    public static String getMessage(int code) {
        for (ErrorCodeEnum error : ErrorCodeEnum.values()) {
            if (error.getCode() == code) {
                return error.getMsg();
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
