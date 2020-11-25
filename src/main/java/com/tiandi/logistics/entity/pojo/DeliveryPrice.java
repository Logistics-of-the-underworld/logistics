package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 配送价格管理
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_delivery_price")
public class DeliveryPrice extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 配送价格编码
     */
    @TableId(value = "id_delivery_price", type = IdType.ASSIGN_ID)
    private String idDeliveryPrice;

    /**
     * 配送点价格or配送范围价格（0.配送点，1.配送范围）
     */
    private Integer spotOrRange;

    /**
     * 首公斤
     */
    private Double firstKilogram;

    /**
     * 次公斤
     */
    private Double secondKilogram;

    /**
     * 首立方
     */
    private Double firstCubic;

    /**
     * 次立方
     */
    private Double secondCubic;

    /**
     * 价格
     */
    private BigDecimal price;


}
