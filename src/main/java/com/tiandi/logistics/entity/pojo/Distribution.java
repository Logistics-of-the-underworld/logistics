package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 配送点管理
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_distribution")
public class Distribution extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tp_distribution", type = IdType.AUTO)
    private Integer idTpDistribution;

    /**
     * 配送点编码
     */
    private Integer idDistribution;

    /**
     * 配送点名称
     */
    private String nameDistribution;

    /**
     * 所属公司
     */
    private String nameCompany;

    /**
     * 管理员代码
     */
    private Integer idAdmin;

    /**
     * 管理员口令
     */
    private String passwordAdmin;

    /**
     * 内部结算价格
     */
    private BigDecimal innerPrice;

    /**
     * 上行站点
     */
    private String upDistributions;

    /**
     * 下行站点
     */
    private String downDistributions;

    /**
     * 是否为主站点（0.否，1.是）
     */
    private Integer isMainDistribution;

    /**
     * 相关主站点
     */
    private String concernedMainDistribution;

    /**
     * 经度
     */
    private String longitude;

    /**
     * 纬度
     */
    private String latitude;

    /**
     * 逻辑删除
     */
    private Integer isDelete;


}
