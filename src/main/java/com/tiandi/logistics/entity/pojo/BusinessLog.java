package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Yue Wu
 * @version 1.0
 * @since 2020/11/30 10:40
 */
@Data
@TableName("tp_business_log")
public class BusinessLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tp_log", type = IdType.AUTO)
    private Integer idTpLog;

    /**
     * 请求者的IP
     */
    private String ipAddress;

    /**
     * 操作时间
     */
    private Date operationTime;

    /**
     * 操作描述
     */
    private String remark;

    /**
     * 执行结果
     */
    private String result;

    /**
     * 系统平台
     */
    private String sysType;

    /**
     * 操作类型
     */
    private String opType;

    /**
     * 操作用户用户名
     */
    private String opUsername;

    /**
     * 操作者身份
     */
    private String opRole;

    /**
     * 请求url
     */
    private String requestUrl;

    /**
     * 请求方法名
     */
    private String methodName;

    /**
     * 是否为异常记录(0:不是，1:是)
     */
    private Integer isException;
}
