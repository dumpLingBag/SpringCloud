package com.rngay;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringCloudApplication
public class PythonApplication {

    public static void main(String[] args) {
        SpringApplication.run(PythonApplication.class, args);
    }

}
