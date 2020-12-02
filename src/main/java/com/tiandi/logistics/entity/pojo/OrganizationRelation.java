package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Yue Wu
 * @version 1.0
 * @since 2020/12/2 13:47
 */
@Data
@TableName("tp_organization_relation")
@ApiModel(value="OrganizationRelation对象", description="用户所属参照表")
public class OrganizationRelation {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tp_or_re", type = IdType.AUTO)
    private Integer idTpOrRe;

    @ApiModelProperty(value = "所属组织名")
    private String organization;

    @ApiModelProperty(value = "对应的用户表外键")
    private Integer user_id;
}
