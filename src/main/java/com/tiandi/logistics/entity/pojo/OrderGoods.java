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
 * 货物分表
 * </p>
 *
 * @author TP
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_order_goods")
@ApiModel(value="OrderGoods对象", description="货物分表")
public class OrderGoods extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tp_order_goods", type = IdType.AUTO)
    private Integer idTpOrderGoods;

    @ApiModelProperty(value = "货物编号")
    private String idGoods;

    @ApiModelProperty(value = "订单编号")
    private String idOrder;

    @ApiModelProperty(value = "货物名称")
    private String nameGoods;

    @ApiModelProperty(value = "数量")
    private Integer countGoods;

    @ApiModelProperty(value = "重量")
    private Double heavy;

    @ApiModelProperty(value = "体积")
    private Double volume;

    @ApiModelProperty(value = "货物类别编号")
    private Integer idSortGoods;

    @ApiModelProperty(value = "逻辑删除")
    private Integer isDelete;


}
