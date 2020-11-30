package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author TP
 * @since 2020-11-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_user")
@ApiModel(value="User对象", description="用户表")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tb_user", type = IdType.AUTO)
    private Integer idTbUser;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "用户昵称")
    private String petname;

    @ApiModelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "权限,外键与role表关联")
    private Integer role;

    @ApiModelProperty(value = "禁用标志位")
    private Integer ban;

    @ApiModelProperty(value = "删除标志位，表示被封禁的用户")
    private Integer isDelete;

    @ApiModelProperty(value = "用户密码盐")
    private String salt;

    @ApiModelProperty(value = "用户激活用UUID标识")
    @TableField("activeUUID")
    private String activeuuid;


}
