package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "message", name = "消息")
public class MessageController {

    @RequestMapping(value = "load/{type}", method = RequestMethod.GET, name = "加载消息")
    public Result<?> load(@PathVariable Integer type) {
        if (type != null) {
            return Result.success();
        }
        return Result.failMsg("获取消息失败");
    }

    @RequestMapping(value = "send", method = RequestMethod.POST, name = "发送消息")
    public Result<?> send(@RequestParam Integer userId, @RequestParam String content) {
        return Result.success();
    }

}
