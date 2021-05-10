package com.rngay.feign.authority.query;

import com.rngay.feign.dto.BaseDTO;

import javax.validation.constraints.NotBlank;

public class RetrieveQuery extends BaseDTO {

    @NotBlank(message = "缺少邮箱")
    private String email;

    @NotBlank(message = "缺少验证码")
    private String code;

    @NotBlank(message = "缺少密码")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
