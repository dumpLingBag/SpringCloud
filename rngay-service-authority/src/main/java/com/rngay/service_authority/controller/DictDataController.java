package com.rngay.service_authority.controller;

import com.rngay.common.vo.Result;
import com.rngay.service_authority.model.DictData;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "dictData")
public class DictDataController {

    @RequestMapping(value = "pageList", method = RequestMethod.GET)
    public Result<?> pageList() {
        return Result.success();
    }

    @RequestMapping(value = "save", method = RequestMethod.POST)
    public Result<Integer> save(@RequestBody DictData dictData) {
        return Result.success();
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public Result<Integer> update(@RequestBody DictData dictData) {
        return Result.success();
    }

}
