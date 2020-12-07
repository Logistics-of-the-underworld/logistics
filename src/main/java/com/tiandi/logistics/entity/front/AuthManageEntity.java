package com.tiandi.logistics.entity.front;

import com.tiandi.logistics.entity.pojo.Role;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author Yue Wu
 * @version 1.0
 * @since 2020/12/2 9:40
 */
@Data
@ApiModel(value="用户管理返回对象", description="针对用户管理使用")
public class AuthManageEntity {

    @ApiModelProperty(value = "用户主键")
    private Integer idTbUser;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "用户昵称")
    private String petname;

    @ApiModelProperty(value = "头像")
    private String icon;

    @ApiModelProperty(value = "用户是否激活")
    private String ban;

    @ApiModelProperty(value = "用户权限实体")
    private Role role;

}
