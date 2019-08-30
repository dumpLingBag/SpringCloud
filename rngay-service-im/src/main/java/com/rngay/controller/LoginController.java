package com.rngay.controller;

import com.rngay.common.cache.RedisUtil;
import com.rngay.common.util.IPUtil;
import com.rngay.common.vo.Result;
import com.rngay.contants.RedisKeys;
import com.rngay.feign.user.dto.UAMemberDTO;
import com.rngay.feign.user.service.MemberService;
import com.rngay.service.UASystemService;
import com.rngay.util.JwtUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping(value = "im")
@RestController
public class LoginController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UASystemService systemService;
    @Autowired
    private HttpServletRequest request;

    @PostMapping(value = "login")
    public Result<?> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return Result.fail("用户名或密码不能为空");
        }

        String key = request.getServerName() + "_" + IPUtil.getIPAddress(request) + "_" + username;
        Object o = redisUtil.get(key);
        int value = 0;
        if (o instanceof Integer) {
            value = (int) o;
        }

        if (value > 5) {
            return Result.fail("出错次数过多，请稍候再试");
        }

        UAMemberDTO member = memberService.findUser(username).getData();
        if (member == null) {
            value++;
            if (value >= 5) {
                redisUtil.set(RedisKeys.getFailCount(key), value, 7200);
            }
            return Result.fail("账号或密码错误");
        } else {
            try {
                if (BCrypt.checkpw(password, member.getPassword())) {
                    redisUtil.del(RedisKeys.getFailCount(key));
                    String token = jwtUtil.generateToken(member.getId());
                    systemService.insertToken(request, member, token);

                    redisUtil.set(RedisKeys.getTokenKey(member.getId()), token);
                    Map<String, Object> map = new HashMap<>();
                    map.put("token", token);
                    map.put("memberResult", member);
                    return Result.success(map);
                } else {
                    value++;
                    if (value >= 5) {
                        redisUtil.set(RedisKeys.getFailCount(key), value, 7200);
                    }
                    return Result.fail("账号或密码错误");
                }
            } catch (IllegalArgumentException e) {
                return Result.fail("账号或密码错误");
            }
        }
    }

}
