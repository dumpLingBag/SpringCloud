package com.rngay.feign.user;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(value = "service-user")
public interface UserService {



}
