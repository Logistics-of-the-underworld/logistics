package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 收入分成参照表
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_revenue_sharing")
public class RevenueSharing extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tp_revenue_sharing", type = IdType.AUTO)
    private Integer idTpRevenueSharing;

    /**
     * 省公司
     */
    private String nameCompany;

    /**
     * 发货配送点
     */
    private String deliverySpot;

    /**
     * 收获配送点
     */
    private String receiveSpot;


}
