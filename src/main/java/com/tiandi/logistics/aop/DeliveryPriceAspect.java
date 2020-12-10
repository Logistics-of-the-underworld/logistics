package com.tiandi.logistics.aop;

import com.alibaba.fastjson.JSON;
import com.tiandi.logistics.aop.log.enumeration.OpTypeEnum;
import com.tiandi.logistics.aop.log.enumeration.SysTypeEnum;
import com.tiandi.logistics.constant.SystemConstant;
import com.tiandi.logistics.entity.pojo.BusinessLog;
import com.tiandi.logistics.entity.pojo.DeliveryPrice;
import com.tiandi.logistics.entity.result.ResultMap;
import com.tiandi.logistics.utils.IpUtil;
import com.tiandi.logistics.utils.JWTUtil;
import com.tiandi.logistics.utils.LogQueue;
import com.tiandi.logistics.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 配送价格管理切面类
 * 用于对价格更新后及时更新缓存，避免缓存数据与数据库中的数据不一致
 *
 * @author Yue Wu
 * @version 1.0
 * @since 2020/12/10 10:06
 */
@Aspect
@Component
@Slf4j
public class DeliveryPriceAspect {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private LogQueue logQueue;

    @Pointcut("execution(public * com.tiandi.logistics.controller.DeliveryPriceController.updateDeliveryPrice(..))")
    public void updatePrice() {
    }

    @AfterReturning(value = "updatePrice()", returning = "result")
    public void afterUpdate(JoinPoint joinPoint, ResultMap result) {
        log.info("==> 进入 修改配送价格同步AOP切面方法");
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String token = request.getHeader("token");
        BusinessLog sysBusinessLog = new BusinessLog();

        //获取请求者IP
        String ip = IpUtil.getIpAddr(request);
        //请求的 类名、方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        String url = request.getRequestURI();
        String param = getParams(joinPoint);

        DeliveryPrice price = JSON.parseObject(param, DeliveryPrice.class);
        redisUtil.set(SystemConstant.TP_PRICE, price);

        sysBusinessLog.setOperationTime(LocalDateTime.now());
        sysBusinessLog.setRequestUrl(url + "&" + param);
        sysBusinessLog.setMethodName(className + "." + methodName + "()");
        sysBusinessLog.setIpAddress(ip);
        sysBusinessLog.setIsException(0);
        sysBusinessLog.setOpUsername(JWTUtil.getUsername(token));
        sysBusinessLog.setOpRole(JWTUtil.getUserRole(token));

        sysBusinessLog.setRemark("修改配送价格同步AOP切面方法");
        sysBusinessLog.setResult(result.get("message").toString());

        sysBusinessLog.setOpType(OpTypeEnum.getMessage(OpTypeEnum.UPDATE.getOpCode()));
        sysBusinessLog.setSysType(SysTypeEnum.getMessage(SysTypeEnum.ADMIN.getSysCode()));

        System.out.println(sysBusinessLog);

        logQueue.add(sysBusinessLog);

        log.info("==> 完成 修改配送价格同步AOP切面方法");

    }

    public String getParams(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        return (String) args[0];
    }
}
