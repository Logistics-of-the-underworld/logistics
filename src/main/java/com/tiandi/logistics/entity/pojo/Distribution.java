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
 * 配送点管理
 * </p>
 *
 * @author TP
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_distribution")
@ApiModel(value="Distribution对象", description="配送点管理")
public class Distribution extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tp_distribution", type = IdType.AUTO)
    private Integer idTpDistribution;

    @ApiModelProperty(value = "配送点编码")
    private Integer idDistribution;

    @ApiModelProperty(value = "配送点名称")
    private String nameDistribution;

    @ApiModelProperty(value = "所属公司")
    private String nameCompany;

    @ApiModelProperty(value = "管理员代码")
    private Integer idAdmin;

    @ApiModelProperty(value = "管理员口令")
    private String passwordAdmin;

    @ApiModelProperty(value = "内部结算价格")
    private BigDecimal innerPrice;

    @ApiModelProperty(value = "上行站点")
    private String upDistributions;

    @ApiModelProperty(value = "下行站点")
    private String downDistributions;

    @ApiModelProperty(value = "是否为主站点（0.否，1.是）")
    private Integer isMainDistribution;

    @ApiModelProperty(value = "相关主站点")
    private String concernedMainDistribution;

    @ApiModelProperty(value = "经度")
    private String longitude;

    @ApiModelProperty(value = "纬度")
    private String latitude;

    @ApiModelProperty(value = "逻辑删除")
    private Integer isDelete;


}
