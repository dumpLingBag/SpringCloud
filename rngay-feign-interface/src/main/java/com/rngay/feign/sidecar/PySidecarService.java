package com.rngay.feign.sidecar;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "py-sidecar")
public interface PySidecarService {

    @RequestMapping(value = "getUser", method = RequestMethod.GET)
    String getUser();

    @RequestMapping(value = "api/login", method = RequestMethod.POST)
    Object login();

}
