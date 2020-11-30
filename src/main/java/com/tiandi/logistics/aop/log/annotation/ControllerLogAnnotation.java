package com.tiandi.logistics.aop.log.annotation;

import com.tiandi.logistics.aop.log.enumeration.OpTypeEnum;
import com.tiandi.logistics.aop.log.enumeration.SysTypeEnum;

import java.lang.annotation.*;

/**
 * 日志注解，需要进行日志处理的Controller层接口使用该注解即可
 *
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/30 9:40
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ControllerLogAnnotation {

    /**
     * @return 操作内容
     */
    String remark() default "";

    /**
     * 0:客户平台
     * 1:管理平台
     * 2:通用方法
     * @return 系统类型
     */
    SysTypeEnum sysType() default SysTypeEnum.CUSTOMER;

    /**
     * 0:登陆
     * 1:新增
     * 2:修改
     * 3:删除
     * 4:查询
     * @return 操作类型
     */
    OpTypeEnum opType() default OpTypeEnum.UPDATE;

    /**
     * @return 是否只存错误信息
     */
    boolean onlyError() default false;
}
