package com.rngay.authority.aspect;

import com.rngay.common.aspect.annotation.RepeatSubmit;
import com.rngay.common.cache.RedisUtil;
import com.rngay.common.config.JwtConfig;
import com.rngay.common.contants.RedisKeys;
import com.rngay.common.exception.BaseException;
import com.rngay.common.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 表单重复提交处理
 * @author pengcheng
 * @date 2020-02-19 13:50
 */
@Aspect
@Component
public class RepeatSubmitAspect {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JwtConfig jwtConfig;
    @Autowired
    private RedisUtil redisUtil;

    @Pointcut("@annotation(com.rngay.common.aspect.annotation.RepeatSubmit)")
    public void repeatSubmitPointCut() {

    }

    @Before("repeatSubmitPointCut()")
    public void commitBefore(JoinPoint point) {
        RepeatSubmit repeatSubmit = getAnnotationLog(point);
        if (repeatSubmit == null) {
            return;
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String requestURI = request.getRequestURI();
            if (StringUtils.isNotBlank(requestURI)) {
                String token = request.getHeader(jwtConfig.getHeader());
                if (StringUtils.isNotBlank(token)) {
                    String subject = jwtUtil.getSubject(jwtUtil.getToken(token));
                    if (StringUtils.isBlank(subject)) {
                        throw new BaseException(401, "用户信息过期，请重新登录");
                    }
                    Object url = redisUtil.get(RedisKeys.getFormCommitKey(subject));
                    if (url != null && url.equals(requestURI)) {
                        throw new BaseException(1, "重复请求");
                    }
                    redisUtil.set(RedisKeys.getFormCommitKey(subject), requestURI, 3);
                } else {
                    throw new BaseException(1, "非法请求");
                }
            }
        }
    }

    private RepeatSubmit getAnnotationLog(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null) {
            return method.getAnnotation(RepeatSubmit.class);
        }
        return null;
    }

}
