package com.rngay.common.exception;

public class BaseException extends RuntimeException {

    private int code;

    public BaseException() {

    }
    public BaseException(int code, String message, Throwable e) {
        super(message, e);
        this.code = code;
    }
    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }
    public BaseException(String message, Throwable e) {
        super(message, e);
        this.code = 500;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
