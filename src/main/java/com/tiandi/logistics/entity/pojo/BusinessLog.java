package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 日志表
 * </p>
 *
 * @author TP
 * @since 2020-11-30
 */
@Data
@TableName("tp_business_log")
@ApiModel(value="BusinessLog对象", description="日志表")
public class BusinessLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tp_log", type = IdType.AUTO)
    private Integer idTpLog;

    @ApiModelProperty(value = "请求者的IP")
    private String ipAddress;

    @ApiModelProperty(value = "操作时间")
    private LocalDateTime operationTime;

    @ApiModelProperty(value = "操作描述")
    private String remark;

    @ApiModelProperty(value = "操作结果")
    private String result;

    @ApiModelProperty(value = "系统平台")
    private String sysType;

    @ApiModelProperty(value = "操作类型")
    private String opType;

    @ApiModelProperty(value = "操作用户用户名")
    private String opUsername;

    @ApiModelProperty(value = "操作者身份")
    private String opRole;

    @ApiModelProperty(value = "请求url")
    private String requestUrl;

    @ApiModelProperty(value = "请求方法名")
    private String methodName;

    @ApiModelProperty(value = "是否为异常记录(0:不是，1:是)")
    private Integer isException;


}
