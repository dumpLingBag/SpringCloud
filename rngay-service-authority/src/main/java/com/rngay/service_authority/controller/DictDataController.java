package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.service_authority.model.DictData;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "dictData")
public class DictDataController {

    @GetMapping(value = "pageList")
    public Result<?> pageList() {
        return Result.success();
    }

    @PostMapping(value = "save")
    public Result<Integer> save(@RequestBody DictData dictData) {
        return Result.success();
    }

    @PutMapping(value = "update")
    public Result<Integer> update(@RequestBody DictData dictData) {
        return Result.success();
    }

}
