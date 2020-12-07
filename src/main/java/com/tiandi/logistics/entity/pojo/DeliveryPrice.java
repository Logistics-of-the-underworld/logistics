package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 配送价格管理
 * </p>
 *
 * @author TP
 * @since 2020-11-30
 */
@Data
@TableName("tp_delivery_price")
@ApiModel(value="DeliveryPrice对象", description="配送价格管理")
public class DeliveryPrice {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "配送价格编码")
    @TableId(value = "id_delivery_price", type = IdType.ASSIGN_ID)
    private String idDeliveryPrice;

    @ApiModelProperty(value = "配送点价格or配送范围价格（0.配送点，1.配送范围）")
    private Integer spotOrRange;

    @ApiModelProperty(value = "首公斤")
    private Double firstKilogram;

    @ApiModelProperty(value = "次公斤")
    private Double secondKilogram;

    @ApiModelProperty(value = "首立方")
    private Double firstCubic;

    @ApiModelProperty(value = "次立方")
    private Double secondCubic;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;


}
