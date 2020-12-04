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
 * 用户权限表
 * </p>
 *
 * @author TP
 * @since 2020-11-30
 */
@Data
@TableName("tp_role")
@ApiModel(value="Role对象", description="用户权限表")
public class Role {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tb_role", type = IdType.AUTO)
    private Integer idTbRole;

    @ApiModelProperty(value = "身份")
    private String role;

    @ApiModelProperty(value = "权限")
    private String permission;

    @ApiModelProperty(value = "权限组描述")
    private String note;

}
