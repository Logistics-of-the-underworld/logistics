package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 配送范围管理
 * </p>
 *
 * @author TP
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_distribution_range")
@ApiModel(value="DistributionRange对象", description="配送范围管理")
public class DistributionRange extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tp_distribution_range", type = IdType.AUTO)
    private Integer idTpDistributionRange;

    @ApiModelProperty(value = "配送范围编码")
    private Integer idRange;

    @ApiModelProperty(value = "配送点编码")
    private Integer idDistribution;

    @ApiModelProperty(value = "范围名称")
    private String nameRange;

    @ApiModelProperty(value = "开始配送时间")
    private LocalDateTime startDeliveryTime;

    @ApiModelProperty(value = "结束配送时间")
    private LocalDateTime endDeliveryTime;

    @ApiModelProperty(value = "备注")
    private String marks;

    @ApiModelProperty(value = "逻辑删除")
    private Integer isDelete;


}
