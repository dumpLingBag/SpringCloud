package com.rngay.common.controller;

import com.rngay.common.exception.BaseException;
import com.rngay.common.vo.Result;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ControllerAdvice
public class RestExceptionHandler implements ErrorController {

    //运行时异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result<?> runtimeExceptionHandler(Exception e) {
        if (e instanceof BaseException) {
            BaseException err = (BaseException) e;
            String msg = err.getMessage();
            return Result.fail(err.getCode(), msg);
        }
        e.printStackTrace();
        return Result.fail(e.toString());
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
