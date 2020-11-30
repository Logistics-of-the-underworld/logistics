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
 * 公司管理
 * </p>
 *
 * @author TP
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_company")
@ApiModel(value="Company对象", description="公司管理")
public class Company extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tp_company", type = IdType.AUTO)
    private Integer idTpCompany;

    @ApiModelProperty(value = "公司编号")
    private String idCompany;

    @ApiModelProperty(value = "公司名")
    private String nameCompany;

    @ApiModelProperty(value = "上级公司，如果值为空则为总公司，如果有值则为分公司")
    private Integer headCompany;

    @ApiModelProperty(value = "逻辑删除")
    private Integer isDelete;


}
