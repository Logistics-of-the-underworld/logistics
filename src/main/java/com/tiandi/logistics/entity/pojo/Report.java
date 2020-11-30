package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 报表管理
 * </p>
 *
 * @author TP
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_report")
@ApiModel(value="Report对象", description="报表管理")
public class Report extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tp_report", type = IdType.AUTO)
    private Integer idTpReport;

    @ApiModelProperty(value = "报表编号")
    private String idReport;

    @ApiModelProperty(value = "配送点编码")
    private Integer idDistribution;

    @ApiModelProperty(value = "配送点名称")
    private String nameDistribution;

    @ApiModelProperty(value = "所属公司")
    private String nameCompany;

    @ApiModelProperty(value = "配送货物总重量")
    private Double countHeavy;

    @ApiModelProperty(value = "配送货物总体积")
    private Double countVolume;

    @ApiModelProperty(value = "配送收入")
    private BigDecimal countPrice;

    @ApiModelProperty(value = "逻辑删除")
    private Integer isDelete;


}
