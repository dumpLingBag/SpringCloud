package com.rngay.service_python.controller;

import com.rngay.common.vo.Result;
import com.rngay.feign.sidecar.PySidecarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PyController {

    @Autowired
    private PySidecarService pySidecarService;

    @RequestMapping(value = "python")
    public Result getUser() {
        return Result.success(pySidecarService.getUser());
    }

}
