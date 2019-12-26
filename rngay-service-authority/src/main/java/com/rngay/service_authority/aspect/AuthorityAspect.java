package com.rngay.service_authority.aspect;

import com.rngay.common.vo.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthorityAspect {

    @Pointcut("@annotation(com.rngay.common.annotation.PreAuthorize)")
    public void addAdvice(){}

    @Around("addAdvice()")
    public Result<?> Interceptor(ProceedingJoinPoint joinPoint) {
        return Result.success();
    }



}
