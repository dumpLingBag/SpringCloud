package com.rngay.authority.security.exception;

import com.rngay.authority.enums.ResultCodeEnum;
import org.springframework.security.core.AuthenticationException;

public class MyAuthenticationException extends AuthenticationException {

    private int code;

    public MyAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public MyAuthenticationException(String msg) {
        super(msg);
    }

    /**
     * 加入错误状态值
     * @param codeEnum
     */
    public MyAuthenticationException(ResultCodeEnum codeEnum) {
        super(codeEnum.getMsg());
        this.code = codeEnum.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}