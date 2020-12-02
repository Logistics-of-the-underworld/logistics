package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 路线管理
 * </p>
 *
 * @author TP
 * @since 2020-12-2
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_line_info")
@ApiModel(value="路线对象", description="路线管理")
public class LineInfo extends BaseEntity{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "路线ID")
    private String idLine;

    @ApiModelProperty(value = "运费")
    private BigDecimal cost;

    @ApiModelProperty(value = "起始点站点编码")
    private Integer startDistribution;

    @ApiModelProperty(value = "终点配送点编码")
    private Integer endDistribution;

    @ApiModelProperty(value = "起点经度")
    private Double startLongitude;

    @ApiModelProperty(value = "起点纬度")
    private Double startLatitude;

    @ApiModelProperty(value = "终点经度")
    private Double endLongitude;

    @ApiModelProperty(value = "终点纬度")
    private Double endLatitude;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer isDelete;
}
