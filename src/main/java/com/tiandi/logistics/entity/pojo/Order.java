package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_order")
@ApiModel(value="Order对象", description="订单管理")
public class Order extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tp_order", type = IdType.AUTO)
    private Integer idTpOrder;

    @ApiModelProperty(value = "订单编号")
    private String idOrder;

    @ApiModelProperty(value = "配送点编码")
    private Integer idDistribution;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "条形码")
    private String barCodeUrl;

    @ApiModelProperty(value = "条形码编号")
    private String idBarCode;

    @ApiModelProperty(value = "订单状态 0.未处理，1.审核通过，2.在途中，3.在配送中，4.已签收，5.等待支付，6.已完成，7.退订")
    private Integer stateOrder;

    @ApiModelProperty(value = "寄件人地址")
    private String senderAddress;

    @ApiModelProperty(value = "寄件人姓名")
    private String senderName;

    @ApiModelProperty(value = "寄件人电话")
    private String senderPhone;

    @ApiModelProperty(value = "收件人地址")
    private String receiverAddress;

    @ApiModelProperty(value = "收件人姓名")
    private String receiverName;

    @ApiModelProperty(value = "收件人电话")
    private String receiverPhone;

    @ApiModelProperty(value = "配送价格")
    private BigDecimal deliveryPrice;

    @ApiModelProperty(value = "保价费")
    private BigDecimal insuranceFee;

    @ApiModelProperty(value = "支付方式")
    private String paymentMethod;

    @ApiModelProperty(value = "发货时间")
    private LocalDateTime deliveryTime;

    @ApiModelProperty(value = "发货车辆")
    private String idLicense;

    @ApiModelProperty(value = "快递员姓名")
    private String courier;

    @ApiModelProperty(value = "快递员电话")
    private String courierPhone;

    @ApiModelProperty(value = "备注")
    private String marks;

    @ApiModelProperty(value = "审核人")
    private String reviewer;

    @ApiModelProperty(value = "用户评价")
    private Integer importance;

    @ApiModelProperty(value = "逻辑删除")
    private Integer isDelete;


}
