package com.rngay.controller;

import com.rngay.common.vo.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "index")
public class IndexController {

    /*@Value(value = "${spring.rabbitmq.username}")
    public static String username;

    @GetMapping(value = "index")
    public Result<?> index() {
        return Result.success("rabbitmq -> username = " + username);
    }*/

}
