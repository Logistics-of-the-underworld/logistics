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
 * 配送点到配送范围价格
 * </p>
 *
 * @author TP
 * @since 2020-11-30
 */
@Data
@TableName("tp_delivery_price_range")
@ApiModel(value="DeliveryPriceRange对象", description="配送点到配送范围价格")
public class DeliveryPriceRange {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "配送价格编码")
    @TableId(value = "id_delivery_price", type = IdType.ASSIGN_ID)
    private String idDeliveryPrice;

    @ApiModelProperty(value = "配送点编码")
    private Integer idDistribution;

    @ApiModelProperty(value = "配送范围编码")
    private Integer idRange;


}
