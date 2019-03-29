package com.rngay.common.controller;

import com.netflix.client.ClientException;
import com.rngay.common.exception.BaseException;
import com.rngay.common.vo.Result;
import feign.RetryableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ControllerAdvice
@Component
public class RestExceptionHandler implements ErrorController {

    @Value(value = "${spring.profiles.active}")
    public String profilesActive;
    @Value(value = "${exception.msg}")
    public Boolean exceptionMsg;

    //运行时异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<?> runtimeExceptionHandler(Exception e) {
        if (e instanceof BaseException) {
            BaseException err = (BaseException) e;
            return Result.fail(err.getCode(), err.getMessage());
        }
        if (e instanceof RetryableException && profilesActive.equals("prod")) {
            return Result.fail("服务更新中");
        }
        if (e instanceof ClientException && profilesActive.equals("prod")) {
            return Result.fail("服务未启动");
        }
        if (e instanceof MethodArgumentNotValidException) {
            StringBuilder msg = new StringBuilder();
            MethodArgumentNotValidException ex = (MethodArgumentNotValidException) e;
            List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
            fieldErrors.forEach(error -> msg.append(error.getField()).append(":").append(error.getDefaultMessage()).append("_"));
            return Result.fail(msg.toString());
        }
        if (exceptionMsg) e.printStackTrace();

        return Result.fail(profilesActive.equals("prod") ? "服务器出小差了" : e.toString());
    }

    @RequestMapping(value = "/error")
    public String handleError() {
        return "/index.html";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}
