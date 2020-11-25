package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 交接单管理
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_handover_slip")
public class HandoverSlip extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tp_handover_slip", type = IdType.AUTO)
    private Integer idTpHandoverSlip;

    /**
     * 订单编号
     */
    private String idOrder;

    /**
     * 封装贷号
     */
    private String idPackage;

    /**
     * 发货地
     */
    private String statSpot;

    /**
     * 交接地
     */
    private String handoverSpot;

    /**
     * 接收人签字
     */
    private String receiverName;

    /**
     * 逻辑删除
     */
    private Integer isDelete;


}
