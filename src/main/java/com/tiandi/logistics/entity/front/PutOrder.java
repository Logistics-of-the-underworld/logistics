package com.tiandi.logistics.entity.front;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author kotori
 * @version 1.0
 * @date 2020/12/7 09:50
 * @description
 */

@Data
public class PutOrder {
    /**
     * 物品编号
     */
    private String idGoods;

    /**
     * 订单编号
     */
    private String idOrder;

    /**
     * 用户
     */
    private String username;

    /**
     * 物品名称
     */
    private String nameGoods;

    /**
     * 物品数量
     */
    private String countGoods;

    /**
     * 货物类别编号
     */
    private Integer idSortGoods;

    /**
     * 寄件人地址
     */
    private String senderAddress;

    /**
     * 寄件人姓名
     */
    private String senderName;

    /**
     * 寄件人电话
     */
    private String senderPhone;

    /**
     * 收件人姓名
     */
    private String receiverName;

    /**
     * 收件人电话
     */
    private String receiverPhone;

    /**
     * 收件人地址
     */
    private String receiverAddress;

    /**
     * 支付方式
     */
    private String paymentMethod;

    /**
     * 配送价格
     */
    private BigDecimal deliveryPrice;

    /**
     * 订单状态
     */
    private Integer stateOrder = 0;

    /**
     * 备注
     */
    private String marks;
}
