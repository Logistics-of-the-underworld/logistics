package com.tiandi.logistics.aop;

import com.alibaba.fastjson.JSON;
import com.tiandi.logistics.aop.log.annotation.ControllerLogAnnotation;
import com.tiandi.logistics.aop.log.enumeration.OpTypeEnum;
import com.tiandi.logistics.aop.log.enumeration.SysTypeEnum;
import com.tiandi.logistics.entity.pojo.BusinessLog;
import com.tiandi.logistics.utils.IpUtil;
import com.tiandi.logistics.utils.JWTUtil;
import com.tiandi.logistics.utils.LogQueue;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 日志切面类，实现Controller层日志的统一管理
 *
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/30 9:50
 */
@Aspect
@Component
@Slf4j
public class ControllerLogAspect {

    @Autowired
    private LogQueue logQueue;

    @Pointcut("@annotation(com.tiandi.logistics.aop.log.annotation.ControllerLogAnnotation)")
    public void logPointCut() {}

    /**
     * AfterRunning: 返回通知 rsult为返回内容
     * @param joinPoint 切入点
     */
    @Before(value="logPointCut()")
    public void before(JoinPoint joinPoint){
        log.info("调用了前置通知");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        handle(joinPoint,null,request);

    }

    /**
     * @AfterThrowing: 异常通知
     * @param joinPoint 切入点
     * @param e 异常
     */
    @AfterThrowing(pointcut="logPointCut()",throwing="e")
    public void afterReturningMethod(JoinPoint joinPoint, Exception e) {
        log.info("调用了异常通知");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        handle(joinPoint,e,request);

    }

    @Async
    public void handle(final JoinPoint joinPoint,final Exception e,final HttpServletRequest request){
        BusinessLog sysBusinessLog = new BusinessLog();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        ControllerLogAnnotation op = method.getAnnotation(ControllerLogAnnotation.class);
        if (op != null){
            sysBusinessLog.setRemark(op.remark());
            sysBusinessLog.setSysType(SysTypeEnum.getMessage(op.sysType().getSysCode()));
            sysBusinessLog.setOpType(OpTypeEnum.getMessage(op.opType().getOpCode()));
        }
        //请求的 类名、方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        String ip = IpUtil.getIpAddr(request);
        String url = request.getRequestURI();
        String param = getParams(joinPoint);

        sysBusinessLog.setOperationTime(new Date());
        sysBusinessLog.setRequestUrl(url + "&"+ param);
        sysBusinessLog.setMethodName(className + "." + methodName + "()");
        sysBusinessLog.setIpAddress(ip);

        if (0 == op.opType().getOpCode()) {
            sysBusinessLog.setOpUsername(joinPoint.getArgs()[0].toString());
            sysBusinessLog.setOpRole("tourist");
        } else {
            String token = request.getHeader("token");
            if (token != null && !"".equals(token)) {
                sysBusinessLog.setOpRole(JWTUtil.getUserRole(token));
            }
        }

        try {
            if (e != null){
                sysBusinessLog.setRemark(e.getMessage());
                sysBusinessLog.setIsException(1);
                logQueue.add(sysBusinessLog);
            }else {
                if (!op.onlyError()){
                    sysBusinessLog.setIsException(0);
                    logQueue.add(sysBusinessLog);
                }
            }
        } catch (Exception ex) {
            log.error("handle systemLog 出现异常",ex);
        }
    }

    public String getParams(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        return JSON.toJSONString(args);
    }

}
