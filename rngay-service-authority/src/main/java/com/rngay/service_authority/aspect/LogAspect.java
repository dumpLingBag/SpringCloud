package com.rngay.service_authority.aspect;

import com.rngay.common.aspect.annotation.Log;
import com.rngay.common.aspect.enums.BusinessStatus;
import com.rngay.common.manager.AsyncManager;
import com.rngay.common.util.JsonUtil;
import com.rngay.common.util.ServletUtils;
import com.rngay.common.util.StringUtils;
import com.rngay.common.util.ip.IPUtil;
import com.rngay.feign.authority.OperationLogDTO;
import com.rngay.feign.user.dto.UaUserDTO;
import com.rngay.service_authority.manger.AsyncFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

@Aspect
@Component
public class LogAspect {

    private static final Logger log = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("@annotation(com.rngay.common.aspect.annotation.Log)")
    public void logPointCut() {}

    /**
     * 处理完请求后执行
     *
     * @param joinPoint 切点
     */
    @AfterReturning(pointcut = "logPointCut()", returning = "jsonResult")
    public void doAfterReturning(JoinPoint joinPoint, Object jsonResult) {
        handleLog(joinPoint, null, jsonResult);
    }

    /**
     * 拦截异常操作
     *
     * @param joinPoint 切点
     * @param e 异常
     */
    @AfterThrowing(value = "logPointCut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Exception e) {
        handleLog(joinPoint, e, null);
    }

    protected void handleLog(final JoinPoint joinPoint, final Exception e, Object jsonResult) {
        try {
            // 获得注解
            Log controllerLog = getAnnotationLog(joinPoint);
            if (controllerLog == null) {
                return;
            }

            // 获取当前的用户
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UaUserDTO uaUserDTO = (UaUserDTO) authentication.getPrincipal();
            if (uaUserDTO != null) {
                // *========数据库日志=========*//
                OperationLogDTO opLog = new OperationLogDTO();
                opLog.setStatus(BusinessStatus.SUCCESS.ordinal());
                // 请求的地址
                String ip = IPUtil.getIPAddress(ServletUtils.getRequest());
                opLog.setOperIp(ip);
                // 返回参数
                opLog.setJsonResult(JsonUtil.obj2String(jsonResult));

                opLog.setOperUrl(ServletUtils.getRequest().getRequestURI());
                opLog.setOperName(uaUserDTO.getUsername());

                if (e != null) {
                    opLog.setStatus(BusinessStatus.FAIL.ordinal());
                    opLog.setErrorMsg(StringUtils.substring(e.getMessage(), 0, 2000));
                }
                // 设置方法名称
                String className = joinPoint.getTarget().getClass().getName();
                String methodName = joinPoint.getSignature().getName();
                opLog.setMethod(className + "." + methodName + "()");
                // 设置请求方式
                opLog.setRequestMethod(ServletUtils.getRequest().getMethod());
                // 处理设置注解上的参数
                getControllerMethodDescription(joinPoint, controllerLog, opLog);
                // 保存数据库
                AsyncManager.me().execute(AsyncFactory.recordOper(opLog));
            }
        } catch (Exception exp) {
            // 记录本地异常日志
            log.error("==前置通知异常==");
            log.error("异常信息:{}", exp.getMessage());
            exp.printStackTrace();
        }
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param log 日志
     * @param opLog 操作日志
     * @throws Exception
     */
    public void getControllerMethodDescription(JoinPoint joinPoint, Log log, OperationLogDTO opLog) throws Exception {
        // 设置action动作
        opLog.setBusinessType(log.businessType().ordinal());
        // 设置标题
        opLog.setTitle(log.title());
        // 设置操作人类别
        opLog.setOperatorType(log.operatorType().ordinal());
        // 是否需要保存request，参数和值
        if (log.isSaveRequestData()) {
            // 获取参数的信息，传入到数据库中。
            setRequestValue(joinPoint, opLog);
        }
    }

    /**
     * 获取请求的参数，放到log中
     *
     * @param operLog 操作日志
     * @throws Exception 异常
     */
    private void setRequestValue(JoinPoint joinPoint, OperationLogDTO operLog) throws Exception {
        String requestMethod = operLog.getRequestMethod();
        if (HttpMethod.PUT.name().equals(requestMethod) || HttpMethod.POST.name().equals(requestMethod)) {
            String params = argsArrayToString(joinPoint.getArgs());
            operLog.setOperParam(StringUtils.substring(params, 0, 2000));
        } else {
            Map<?, ?> paramsMap = (Map<?, ?>) ServletUtils.getRequest().getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            operLog.setOperParam(StringUtils.substring(paramsMap.toString(), 0, 2000));
        }
    }

    /**
     * 是否存在注解，如果存在就获取
     */
    private Log getAnnotationLog(JoinPoint joinPoint) throws Exception {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        if (method != null) {
            return method.getAnnotation(Log.class);
        }
        return null;
    }

    /**
     * 参数拼装
     */
    private String argsArrayToString(Object[] paramsArray) {
        StringBuilder params = new StringBuilder();
        if (paramsArray != null && paramsArray.length > 0) {
            for (Object o : paramsArray) {
                if (!isFilterObject(o)) {
                    String jsonObj = JsonUtil.obj2String(o);
                    params.append(jsonObj).append(" ");
                }
            }
        }
        return params.toString().trim();
    }

    /**
     * 判断是否需要过滤的对象。
     *
     * @param o 对象信息。
     * @return 如果是需要过滤的对象，则返回true；否则返回false。
     */
    public boolean isFilterObject(final Object o) {
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }

}
