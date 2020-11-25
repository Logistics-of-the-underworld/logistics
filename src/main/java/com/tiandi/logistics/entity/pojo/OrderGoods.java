package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 货物分表
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_order_goods")
public class OrderGoods extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tp_order_goods", type = IdType.AUTO)
    private Integer idTpOrderGoods;

    /**
     * 货物编号
     */
    private String idGoods;

    /**
     * 订单编号
     */
    private String idOrder;

    /**
     * 货物名称
     */
    private String nameGoods;

    /**
     * 数量
     */
    private Integer countGoods;

    /**
     * 重量
     */
    private Double heavy;

    /**
     * 体积
     */
    private Double volume;

    /**
     * 货物类别编号
     */
    private Integer idSortGoods;

    /**
     * 逻辑删除
     */
    private Integer isDelete;


}
