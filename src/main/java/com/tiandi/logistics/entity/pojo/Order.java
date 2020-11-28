package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单管理
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_order")
public class Order extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tp_order", type = IdType.AUTO)
    private Integer idTpOrder;

    /**
     * 订单编号
     */
    private String idOrder;

    /**
     * 配送点编码
     */
    private Integer idDistribution;

    /**
     * 条形码
     */
    private String barCodeUrl;

    /**
     * 条形码编号
     */
    private Integer idBarCode;

    /**
     * 订单状态（0.未处理，1.已接受，2.在途中，3.在配送中，4.已签收，5.等待支付，6.已完成，7.退订）
     */
    private Integer stateOrder;

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
     * 收件人地址
     */
    private String receiverAddress;

    /**
     * 收件人姓名
     */
    private String receiverName;

    /**
     * 收件人电话
     */
    private String receiverPhone;

    /**
     * 配送价格
     */
    private BigDecimal deliveryPrice;

    /**
     * 保价费
     */
    private BigDecimal insuranceFee;

    /**
     * 支付方式
     */
    private String paymentMethod;

    /**
     * 发货时间
     */
    private LocalDateTime deliveryTime;

    /**
     * 发货车辆
     */
    private String idLicense;

    /**
     * 快递员姓名
     */
    private String courier;

    /**
     * 快递员电话
     */
    private String courierPhone;

    /**
     * 备注
     */
    private String marks;

    /**
     * 审核人
     */
    private String reviewer;

    /**
     * 逻辑删除
     */
    private Integer isDelete;


}
