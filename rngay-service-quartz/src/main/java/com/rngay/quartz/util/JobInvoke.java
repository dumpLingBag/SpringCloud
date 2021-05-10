package com.rngay.quartz.util;

import com.rngay.common.util.StringUtils;
import com.rngay.common.util.spring.SpringUtils;
import com.rngay.feign.quartz.dto.JobDTO;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class JobInvoke {

    /**
     * 执行方法
     * @param jobDTO: 系统任务
     * @return: void
     * @date 2021/5/5 下午8:47
     * @auther dongpengcheng
     */
    public void invokeMethod(JobDTO jobDTO) throws Exception {
        String invokeTarget = jobDTO.getInvokeTarget();
        String beanName = getBeanName(invokeTarget);
        String methodName = getMethodName(invokeTarget);
        List<Object[]> methodParams = getMethodParams(invokeTarget);
        Object bean;
        if (!isValidClassName(invokeTarget)) {
            bean = SpringUtils.getBean(beanName);
        } else {
            bean = Class.forName(beanName).newInstance();
        }
        invokeMethod(bean, methodName, methodParams);
    }

    /**
     * 调用任务方法
     * @param bean: 目标对象
     * @param methodName: 方法名称
     * @param methodParams: 方法参数
     * @return: void
     * @date 2021/5/5 下午8:47
     * @auther dongpengcheng
     */
    public void invokeMethod(Object bean, String methodName, List<Object[]> methodParams) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (methodParams.size() > 0) {
            Method method = bean.getClass().getDeclaredMethod(methodName, getMethodParamsType(methodParams));
            method.invoke(bean, getMethodParamsValue(methodParams));
        } else {
            Method method = bean.getClass().getDeclaredMethod(methodName);
            method.invoke(bean);
        }
    }

    /**
     * 获取bean名称
     * @param invokeTarget: 目标字符串
     * @return: java.lang.String
     * @date 2021/5/5 下午8:47
     * @auther dongpengcheng
     */
    public String getBeanName(String invokeTarget) {
        String beanName = StringUtils.substringBefore(invokeTarget, "(");
        return StringUtils.substringBeforeLast(beanName, ".");
    }

    /**
     * 获取bean方法
     * @param invokeTarget: 目标字符串
     * @return: java.lang.String
     * @date 2021/5/5 下午8:46
     * @auther dongpengcheng
     */
    public String getMethodName(String invokeTarget) {
        String methodName = StringUtils.substringBefore(invokeTarget, "(");
        return StringUtils.substringAfterLast(methodName, ".");
    }

    /**
     * 获取method方法参数相关列表
     * @param invokeTarget: 目标字符串
     * @return: java.util.List<java.lang.Object[]>
     * @date 2021/5/5 下午8:46
     * @auther dongpengcheng
     */
    public List<Object[]> getMethodParams(String invokeTarget) {
        String methodStr = StringUtils.substringBetween(invokeTarget, "(", ")");
        if (StringUtils.isEmpty(methodStr)) {
            return new ArrayList<>();
        }
        String[] methodParams = methodStr.split(",");
        List<Object[]> clazz = new LinkedList<>();
        for (String methodParam : methodParams) {
            String str = StringUtils.trimToEmpty(methodParam);
            if (StringUtils.contains(str, "'")) {
                clazz.add(new Object[]{StringUtils.replace(str, "'", ""), String.class});
            } else if (StringUtils.equals(str, "true") || StringUtils.equalsIgnoreCase(str, "false")) {
                clazz.add(new Object[]{Boolean.valueOf(str), Boolean.class});
            } else if (StringUtils.containsIgnoreCase(str, "L")) {
                clazz.add(new Object[]{StringUtils.replace(str, "L", ""), Long.class});
            } else if (StringUtils.containsIgnoreCase(str, "D")) {
                clazz.add(new Object[]{StringUtils.replace(str, "D", ""), Double.class});
            } else {
                clazz.add(new Object[]{Integer.parseInt(str), Integer.class});
            }
        }
        return clazz;
    }

    /**
     * 检查是否是class包名
     * @param invokeTarget:
     * @return: boolean
     * @date 2021/5/5 下午8:33
     * @auther dongpengcheng
     */
    private boolean isValidClassName(String invokeTarget) {
        return StringUtils.countMatches(invokeTarget, ".") > 1;
    }

    /**
     * 获取参数类型
     * @param methodParams: 参数列表
     * @return: java.lang.Class<?>[]
     * @date 2021/5/5 下午8:45
     * @auther dongpengcheng
     */
    private Class<?>[] getMethodParamsType(List<Object[]> methodParams) {
        Class<?>[] clazz = new Class<?>[methodParams.size()];
        int index = 0;
        for (Object[] os : methodParams) {
            clazz[index++] = (Class<?>) os[1];
        }
        return clazz;
    }

    /**
     * 获取参数值
     * @param methodParams: 参数列表
     * @return: java.lang.Object[]
     * @date 2021/5/5 下午8:46
     * @auther dongpengcheng
     */
    private Object[] getMethodParamsValue(List<Object[]> methodParams) {
        Object[] clazz = new Object[methodParams.size()];
        int index = 0;
        for (Object[] os : methodParams) {
            clazz[index++] = os[0];
        }
        return clazz;
    }

}
