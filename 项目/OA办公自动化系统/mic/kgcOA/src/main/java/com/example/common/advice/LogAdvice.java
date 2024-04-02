package com.example.common.advice;

import com.example.common.annotation.OALog;
import com.example.common.utils.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author 丁祥瑞
 * 日志增强
 * 切面: 切点+增强
 * 切点: 切到使用MesLog注解的方法上
 * 增强: 前置, 后置, 异常, 环绕, 最终
 */
@Component
@Aspect
@Slf4j
public class LogAdvice {

    @Pointcut("@annotation(com.example.common.annotation.OALog)")
    public void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long times1 = System.currentTimeMillis();
        Object object = joinPoint.proceed();
        long times2 = System.currentTimeMillis();
        long costTime = times2 - times1;
        saveLog(joinPoint, costTime);
        return object;
    }

    /**
     * 保存用户的操作日志信息
     *
     * @param joinPoint 切点
     * @param costTime  目标方法执行的时间
     */
    private void saveLog(ProceedingJoinPoint joinPoint, long costTime) {
        // 找到目标方法(使用了@MesLog注解的方法)
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        // 获得目标方法信息
        Method m = signature.getMethod();
        // 获取目标方法上的MsgLog注解
        OALog oaLog = m.getAnnotation(OALog.class);
        // 从注解中获得方法的日志
        String logType = oaLog.logType().getMsg();
        String operatorType = oaLog.operatorType().getMsg();
        String module = oaLog.module().getMsg();
        String content = oaLog.content();
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        String requestUrl = request.getRequestURI();
    }
}
