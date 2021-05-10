package com.rngay.authority.controller;

import com.rngay.authority.util.EmailUtil;
import com.rngay.authority.util.MailUtil;
import com.rngay.common.cache.RedisUtil;
import com.rngay.common.contants.RedisKeys;
import com.rngay.common.manager.AsyncManager;
import com.rngay.common.util.MessageUtils;
import com.rngay.common.util.StringUtils;
import com.rngay.common.vo.Result;
import com.rngay.feign.authority.MailDTO;
import com.wf.captcha.SpecCaptcha;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.TimerTask;
import java.util.UUID;

@RestController
@RequestMapping(value = "login", name = "登录")
public class LoginController {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MailUtil mailUtil;

    @GetMapping(value = "captcha")
    public Result<HashMap<String, Object>> captcha() {
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
            return Result.failMsg(MessageUtils.message("user.captcha.fail"));
        }
    }

    @GetMapping(value = "verify")
    public Result<String> verify(String email) {
        if (StringUtils.isNoneBlank(email)) {
            AsyncManager.me().execute(new TimerTask() {
                @Override
                public void run() {
                    String random = RandomStringUtils.random(4, "0123456789");
                    redisUtil.set(RedisKeys.getVerify(email), random, 10 * 60);
                    MailDTO mailDTO = new MailDTO();
                    mailDTO.setRecipient(email);
                    mailDTO.setSubject("找回密码");
                    mailDTO.setContent(EmailUtil.verify(random));
                    mailUtil.sendHtmlMail(mailDTO);
                }
            });
            return Result.success();
        }
        return Result.failMsg("验证码发送失败");
    }



}
