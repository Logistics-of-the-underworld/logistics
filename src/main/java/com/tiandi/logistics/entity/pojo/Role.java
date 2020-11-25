package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 用户权限表
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_role")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tb_role", type = IdType.AUTO)
    private Integer idTbRole;

    /**
     * 身份
     */
    private String role;

    /**
     * 权限
     */
    private String permission;


}
