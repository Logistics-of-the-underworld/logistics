package com.tiandi.logistics.entity.front;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 车辆管理返回实体
 *
 * @author Yue Wu
 * @version 1.0
 * @since 2020/12/7 14:40
 */
@Data
@ApiModel(value="车辆管理返回对象", description="针对车辆管理使用")
public class VehicleResult {

    @ApiModelProperty(value = "用户主键")
    private Integer idTpVehicle;

    @ApiModelProperty(value = "车牌号")
    private String idLicense;

    @ApiModelProperty(value = "驾驶员")
    private String driverName;

    @ApiModelProperty(value = "型号")
    private String type;

    @ApiModelProperty(value = "0.空闲，1.繁忙")
    private Integer state;

    @ApiModelProperty(value = "所属配送点名称")
    private String distributionName;

    @ApiModelProperty(value = "购买时间")
    private String createTime;

    @ApiModelProperty(value = "限载")
    private Double limitKilogram;

    @ApiModelProperty(value = "限容")
    private Double limitVolume;
}
