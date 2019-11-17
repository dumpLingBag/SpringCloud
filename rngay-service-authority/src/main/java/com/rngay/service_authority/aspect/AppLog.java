package com.rngay.service_authority.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class AppLog {

    @Before("execution(* com.rngay.service_authority.controller.*.*(..))")
    public void log(JoinPoint joinPoint) throws NoSuchMethodException {
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        System.out.println(method.getName());
        String name = joinPoint.getSignature().getName();
        System.out.println(name);
    }

}
