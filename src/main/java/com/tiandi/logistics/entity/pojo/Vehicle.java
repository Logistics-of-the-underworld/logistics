package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 车辆管理
 * </p>
 *
 * @author Feng Chen
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_vehicle")
@ApiModel(value="Vehicle对象", description="车辆管理")
public class Vehicle extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tp_vehicle", type = IdType.AUTO)
    private Integer idTpVehicle;

    @ApiModelProperty(value = "车牌号")
    private String idLicense;

    @ApiModelProperty(value = "驾驶员")
    private String driverName;

    @ApiModelProperty(value = "型号")
    private String type;

    @ApiModelProperty(value = "0.空闲，1.繁忙")
    private Integer state;

    @ApiModelProperty(value = "所属配送点编码")
    private String idDistribution;

    @ApiModelProperty(value = "限载")
    private Double limitKilogram;

    @ApiModelProperty(value = "限容")
    private Double limitVolume;

    @ApiModelProperty(value = "逻辑删除")
    private Integer isDelete;


}
