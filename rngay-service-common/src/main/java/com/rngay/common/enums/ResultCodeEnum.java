package com.rngay.common.enums;

public enum ResultCodeEnum {

    TOKEN_INVALID(401, "用户信息已过期，请重新登录"),
    TOKEN_OTHER_LOGIN(2, "账号在其他地方登录，请重新登录"),
    LOGIN_FAIL(2, "登录失败，请重试"),
    LOGIN_COUNT_FAIL(2, "用户名或密码输入错误，请重新输入"),
    LOGIN_INFO_FAIL(2, "更新用户信息失败"),
    COMMON_AUTHORITY_FAIL(2, "公共权限修改失败"),
    UPLOAD_FAIL(2, "文件上传失败"),

    SUCCESS(0, "success"),
    FAIL(1, "failed"),
    FAIL_MSG(2, "fail msg");

    private String msg;
    private int code;

    ResultCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMessage(int code) {
        for (ResultCodeEnum error : ResultCodeEnum.values()) {
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
