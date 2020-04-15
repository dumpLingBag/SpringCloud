package com.rngay.authority.controller;

import com.rngay.common.vo.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "message")
public class MessageController {

    @GetMapping(value = "load/{type}")
    public Result<?> load(@PathVariable Integer type) {
        if (type != null) {
            return Result.success();
        }
        return Result.failMsg("获取消息失败");
    }

    @PostMapping(value = "send")
    public Result<?> send(@RequestParam Integer userId, @RequestParam String content) {
        return Result.success();
    }

}
