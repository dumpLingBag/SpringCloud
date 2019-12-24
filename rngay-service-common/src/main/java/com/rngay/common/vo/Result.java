package com.rngay.common.vo;

import java.io.Serializable;

public class Result<T> implements Serializable {

    public static final int CODE_NOT_LOGIN = -1;
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_FAIL = 1;
    public static final int CODE_FAIL_MSG = 2;

    private int code;
    private T data;
    private String msg;

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Result() {}

    private Result(int code) {
        this.code = code;
    }

    private Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private Result(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(CODE_FAIL, msg);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(code, msg);
    }

    public static <T> Result<T> failMsg(String msg) {
        return new Result<>(CODE_FAIL_MSG, msg);
    }

    public static <T> Result<T> success(int code, String msg) {
        return new Result<>(code, msg);
    }

    public static <T> Result<T> success() {
        return new Result<>(CODE_SUCCESS);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(CODE_SUCCESS, data, "ok");
    }

    public static <T> Result<T> success(T data, String msg) {
        return new Result<>(CODE_SUCCESS, data, msg);
    }
}
