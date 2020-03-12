package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.service_authority.model.DictType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "dictType")
public class DictTypeController {

    @GetMapping(value = "pageList")
    public Result<?> pageList() {
        return Result.success();
    }

    @PostMapping(value = "save")
    public Result<Integer> save(@RequestBody DictType dictType) {
        return Result.success();
    }

    @PutMapping(value = "update")
    public Result<Integer> update(@RequestBody DictType dictType) {
        return Result.success();
    }

}
