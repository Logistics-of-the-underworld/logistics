package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 货物类别管理
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_goods_sort")
public class GoodsSort extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tp_goods_sort", type = IdType.AUTO)
    private Integer idTpGoodsSort;

    /**
     * 货物类别编号（自增）
     */
    private Integer idSortGoods;

    /**
     * 货物类别名称
     */
    private String sortGoodsName;

    /**
     * 逻辑删除
     */
    private Integer isDelete;


}
