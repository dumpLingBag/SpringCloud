package com.rngay.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class DefaultHystrixController {

    @RequestMapping("/defaultFallback")
    public Map<String,String> defaultFallback(){
        Map<String,String> map = new HashMap<>();
        map.put("code", "-1");
        map.put("msg", "服务异常");
        map.put("data", null);
        return map;
    }

}
