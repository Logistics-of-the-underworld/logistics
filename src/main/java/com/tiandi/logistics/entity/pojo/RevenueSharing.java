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
 * 收入分成参照表
 * </p>
 *
 * @author TP
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_revenue_sharing")
@ApiModel(value="RevenueSharing对象", description="收入分成参照表")
public class RevenueSharing extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tp_revenue_sharing", type = IdType.AUTO)
    private Integer idTpRevenueSharing;

    @ApiModelProperty(value = "省公司")
    private String nameCompany;

    @ApiModelProperty(value = "发货配送点")
    private String deliverySpot;

    @ApiModelProperty(value = "收获配送点")
    private String receiveSpot;


}
