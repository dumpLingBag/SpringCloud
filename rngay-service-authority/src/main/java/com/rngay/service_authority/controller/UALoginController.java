package com.rngay.service_authority.controller;

import com.rngay.common.cache.RedisUtil;
import com.rngay.common.exception.BaseException;
import com.rngay.feign.user.dto.UAUserDTO;
import com.rngay.feign.user.service.PFUserService;
import com.rngay.common.vo.Result;
import com.rngay.service_authority.contants.RedisKeys;
import com.rngay.service_authority.service.UASystemService;
import com.rngay.service_authority.util.AuthorityUtil;
import com.rngay.service_authority.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result<Map<String, Object>> login(HttpServletRequest request, String account, String password){
        if (account == null || "".equals(account) || password == null || "".equals(password)) {
            return Result.failMsg("账号或密码不能为空");
        }

        String key = request.getServerName() + "_" + AuthorityUtil.getIPAddress(request) + "_" + account;
        Object o = redisUtil.get(RedisKeys.getFailCount(key));
        int value = 0;
        if (o instanceof Integer) {
            value = (int) o;
        }

        if (value >= 5) {
            //超过次数进行操作，限制登录，或者验证码登录
            return Result.failMsg("出错次数过多，请两小时后再试！");
        }

        UAUserDTO userResult = userService.findByAccount(account).getData();
        if (userResult == null) {
            value ++;
            if (value >= 5) {
                redisUtil.set(RedisKeys.getFailCount(key), value, 7200);
            }
            return Result.failMsg("用户名不存在");
        } else {
            try {
                if (BCrypt.checkpw(password, userResult.getPassword())) {
                    redisUtil.del(RedisKeys.getFailCount(key));
                    if (!account.equals("admin") && userResult.getEnable().equals(0)){
                        return Result.fail("账号被禁用！");
                    }
                    String token = jwtUtil.generateToken(userResult.getId());
                    systemService.insertToken(request, userResult, token);

                    redisUtil.set(RedisKeys.getTokenKey(userResult.getId()), token);
                    Map<String, Object> map = new HashMap<>();
                    map.put("token", token);
                    map.put("userResult", userResult);
                    return Result.success(map);
                } else {
                    return Result.failMsg("账号或密码错误");
                }
            } catch (IllegalArgumentException e) {
                throw new BaseException(401, "账号或密码错误");
            }
        }
    }

}
