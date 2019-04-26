package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "message")
public class MessageController {

    @RequestMapping(value = "load/{type}", method = RequestMethod.GET)
    public Result<?> load(@PathVariable Integer type) {
        if (type != null) {
            return Result.success();
        }
        return Result.failMsg("获取消息失败");
    }

    @RequestMapping(value = "send", method = RequestMethod.POST)
    public Result<?> send(@RequestParam Integer userId, @RequestParam String content) {
        return Result.success();
    }

}
