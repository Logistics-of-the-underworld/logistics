package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_user")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tb_user", type = IdType.AUTO)
    private Integer idTbUser;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 用户昵称
     */
    private String petname;

    /**
     * 头像
     */
    private String icon;

    /**
     * 权限
     */
    private Integer role;

    /**
     * 禁用标志位
     */
    private Integer ban;

    /**
     * 删除标志位，表示被封禁的用户
     */
    private Integer isDelete;

    /**
     * 用户密码盐
     */
    private String salt;

    /**
     * 用户激活用UUID标识
     */
    @TableField("activeUUID")
    private String activeuuid;


}
