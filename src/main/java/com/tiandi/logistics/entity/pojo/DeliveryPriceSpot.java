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
 * 站点到站点配送价格
 * </p>
 *
 * @author TP
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_delivery_price_spot")
@ApiModel(value="DeliveryPriceSpot对象", description="站点到站点配送价格")
public class DeliveryPriceSpot extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "配送价格编码")
    @TableId(value = "id_delivery_price", type = IdType.ASSIGN_ID)
    private String idDeliveryPrice;

    @ApiModelProperty(value = "配送点编码")
    private Integer idDistribution;

    @ApiModelProperty(value = "发货配送点")
    private Integer idStartDistribution;

    @ApiModelProperty(value = "收获配送点")
    private Integer idReceiveDistribution;


}
