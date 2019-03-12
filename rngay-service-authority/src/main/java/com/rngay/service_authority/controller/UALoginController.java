package com.rngay.service_authority.controller;

import com.alibaba.fastjson.JSONObject;
import com.rngay.common.cache.RedisUtil;
import com.rngay.feign.user.dto.UAUserDTO;
import com.rngay.feign.user.service.PFUserService;
import com.rngay.common.vo.Result;
import com.rngay.service_authority.service.UASystemService;
import com.rngay.service_authority.util.AuthorityUtil;
import com.rngay.service_authority.util.HttpClientUtil;
import com.rngay.service_authority.util.HttpsClientRequestFactory;
import com.rngay.service_authority.util.JwtUtil;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "authorityLogin")
public class UALoginController {

    @Resource
    private UASystemService systemService;
    @Resource
    private PFUserService userService;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private JwtUtil jwtUtil;

    @RequestMapping(value = "login")
    public Result<Map<String, Object>> login(HttpServletRequest request, String account, String password){
        if (account == null || "".equals(account) || password == null || "".equals(password)) {
            return Result.fail("账号或密码不能为空");
        }

        String key = request.getServerName() + "_" + AuthorityUtil.getIPAddress(request) + "_" + account;
        Object o = redisUtil.get(key);
        int value = 0;
        if (o instanceof Integer) {
            value = (int) o;
        }

        if (value >= 5) {
            //超过次数进行操作，限制登录，或者验证码登录
            return Result.fail("出错次数过多，请两小时后再试！");
        }

        UAUserDTO userResult = userService.find(account, password).getData();
        if (userResult == null) {
            value ++;
            if (value >= 5) {
                redisUtil.set(key, value, 7200);
            }
            return Result.fail("用户名或密码错误");
        } else {
            if (!account.equals("admin") && userResult.getEnable().equals(0)){
                return Result.fail("账号被禁用！");
            }
            redisUtil.del(key);
            String token = jwtUtil.generateToken(userResult.getId());
            systemService.insertToken(request, userResult, token);

            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("userResult", userResult);
            return Result.success(map);
        }
    }

}
