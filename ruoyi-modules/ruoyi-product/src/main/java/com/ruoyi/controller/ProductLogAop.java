package com.ruoyi.controller;


import com.ruoyi.domain.SysProductLog;
import com.ruoyi.service.SysProductLogService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
@Slf4j
@Order(1)
public class ProductLogAop {

    @Autowired
    SysProductLogService productLogService;

    @Around("execution(* com.ruoyi.controller.*.*(..))")
    public Object ProductListAop(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        SysProductLog sysProductLog = new SysProductLog();

        Object proceed = joinPoint.proceed();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        long endTime = System.currentTimeMillis();
        StringBuffer requestURL = request.getRequestURL();
        Integer status=null;
        if (proceed.toString().equals("")){
            status=1;
        }else {
            status=0;
        }
        sysProductLog.setScopeIp(request.getRemoteUser());
        sysProductLog.setSpendTime((int) (endTime-startTime));
        sysProductLog.setLogInformetion(proceed.toString());
        sysProductLog.setStatus(status);
//        sysProductLog.setExceptionInfo(request);
        productLogService.save(sysProductLog);
        return proceed;
    }
}
