package com.rngay.service_authority.aspect;

import com.rngay.common.aspect.annotation.DataScope;
import com.rngay.common.cache.RedisUtil;
import com.rngay.common.config.JwtConfig;
import com.rngay.common.contants.RedisKeys;
import com.rngay.common.exception.BaseException;
import com.rngay.common.util.JwtUtil;
import com.rngay.feign.user.dto.UaUserDTO;
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

@Aspect
@Component
public class DataScopeAspect {

    public static final String DATA_SCOPE_ALL = "1";

    public static final String DATE_SCOPE_CUSTOM = "2";

    public static final String DATE_SCOPE_DEPT = "3";

    public static final String DATA_SCOPE_DEPT_AND_CHILD = "4";

    public static final String DATA_SCOPE_SELF = "5";

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private JwtConfig jwtConfig;

    @Pointcut("@annotation(com.rngay.common.aspect.annotation.DataScope)")
    public void dataScopePointCut() {

    }

    @Before("dataScopePointCut()")
    public void scopeBefore(JoinPoint point) {
        DataScope dataScope = getAnnotationLog(point);
        if (dataScope == null) {
            return;
        }
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader(jwtConfig.getHeader());
            if (token == null) {
                token = request.getParameter(jwtConfig.getHeader());
            }
            long userId;
            try {
                userId = Long.parseLong(jwtUtil.getSubject(token));
            } catch (Exception e) {
                throw new BaseException(401, "获取用户信息失败");
            }
            UaUserDTO userDTO = (UaUserDTO) redisUtil.get(RedisKeys.getTokenKey(userId));
            if (userDTO != null && userDTO.getParentId() != 0) {
                dataScopeFilter(point, userDTO, dataScope.deptAlias(), dataScope.userAlias());
            }
        }
    }

    public void dataScopeFilter(JoinPoint point, UaUserDTO userDTO, String deptAlias, String userAlias) {
        StringBuilder sql = new StringBuilder();
    }

    private DataScope getAnnotationLog(JoinPoint joinPoint)
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null)
        {
            return method.getAnnotation(DataScope.class);
        }
        return null;
    }

}
