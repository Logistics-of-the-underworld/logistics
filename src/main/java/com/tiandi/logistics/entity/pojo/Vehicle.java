package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 车辆管理
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_vehicle")
public class Vehicle extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tp_vehicle", type = IdType.AUTO)
    private Integer idTpVehicle;

    /**
     * 车牌号
     */
    private String idLicense;

    /**
     * 驾驶员
     */
    private String driverName;

    /**
     * 型号
     */
    private String type;

    /**
     * 0.空闲，1.繁忙
     */
    private Integer state;

    /**
     * 所属配送点编码
     */
    private String idDistribution;

    /**
     * 限载
     */
    private Double limitKilogram;

    /**
     * 限容
     */
    private Double limitVolume;

    /**
     * 逻辑删除
     */
    private Integer isDelete;


}
