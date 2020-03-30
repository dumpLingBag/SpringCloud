package com.rngay.service_authority.controller;

import com.rngay.common.cache.RedisUtil;
import com.rngay.common.vo.Result;
import com.wf.captcha.SpecCaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.UUID;

@RestController
@RequestMapping(value = "login", name = "登录")
public class LoginController {

    @Autowired
    private RedisUtil redisUtil;

    @GetMapping(value = "captcha")
    public Result<?> captcha() {
        try {
            SpecCaptcha captcha = new SpecCaptcha(130, 42);

            String key = UUID.randomUUID().toString();
            // 验证码一分钟内有效
            redisUtil.set(key, captcha.text(), 60);
            HashMap<String, Object> map = new HashMap<>();
            map.put("key", key);
            map.put("code", captcha.toBase64());
            return Result.success(map);
        } catch (Exception e) {
            return Result.failMsg("获取验证码失败");
        }
    }

}
