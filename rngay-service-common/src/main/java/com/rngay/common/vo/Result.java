package com.rngay.common.vo;

import com.rngay.common.contants.ResultCode;

import java.io.Serializable;

/**
 * 返回结果封装
 * @Author: pengcheng
 * @Date: 2020/4/15
 */
public class Result<T> implements Serializable {

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

    private Result(int code, T data) {
        this.code = code;
        this.data = data;
    }

    private Result(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> Result<T> fail() {
        return new Result<>(ResultCode.FAIL);
    }

    public static <T> Result<T> fail(String msg) {
        return new Result<>(ResultCode.FAIL, msg);
    }

    public static <T> Result<T> fail(int code, String msg) {
        return new Result<>(code, msg);
    }

    public static <T> Result<T> failMsg(String msg) {
        return new Result<>(ResultCode.FAIL_MSG, msg);
    }

    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS);
    }

    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS, data);
    }

    public static <T> Result<T> success(T data, String msg) {
        return new Result<>(ResultCode.SUCCESS, data, msg);
    }
}
