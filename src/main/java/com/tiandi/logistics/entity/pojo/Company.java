package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 公司管理
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_company")
public class Company extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tp_company", type = IdType.AUTO)
    private Integer idTpCompany;

    /**
     * 公司编号
     */
    private String idCompany;

    /**
     * 公司名
     */
    private String nameCompany;

    /**
     * 上级公司，如果值为空则为总公司，如果有值则为分公司
     */
    private String headCompany;

    /**
     * 逻辑删除
     */
    private Integer isDelete;


}
