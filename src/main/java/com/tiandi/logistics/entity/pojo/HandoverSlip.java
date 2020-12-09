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
 * 交接单管理
 * </p>
 *
 * @author TP
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_handover_slip")
@ApiModel(value="HandoverSlip对象", description="交接单管理")
public class HandoverSlip extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tp_handover_slip", type = IdType.AUTO)
    private Integer idTpHandoverSlip;

    @ApiModelProperty(value = "订单编号")
    private String idHandoverOrder;

    @ApiModelProperty(value = "封装贷号")
    private String idPackage;

    @ApiModelProperty(value = "发货地")
    private String statSpot;

    @ApiModelProperty(value = "交接地")
    private String handoverSpot;

    @ApiModelProperty(value = "接收人签字")
    private String receiverName;

    @ApiModelProperty(value = "逻辑删除")
    private Integer isDelete;


}
