package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 配送点到配送范围价格
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_delivery_price_range")
public class DeliveryPriceRange extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 配送价格编码
     */
    @TableId(value = "id_delivery_price", type = IdType.ASSIGN_ID)
    private String idDeliveryPrice;

    /**
     * 配送点编码
     */
    private Integer idDistribution;

    /**
     * 配送范围编码
     */
    private Integer idRange;


}
