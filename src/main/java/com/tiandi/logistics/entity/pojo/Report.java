package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * <p>
 * 报表管理
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_report")
public class Report extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tp_report", type = IdType.AUTO)
    private Integer idTpReport;

    /**
     * 报表编号
     */
    private String idReport;

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
     * 配送货物总重量
     */
    private Double countHeavy;

    /**
     * 配送货物总体积
     */
    private Double countVolume;

    /**
     * 配送收入
     */
    private BigDecimal countPrice;

    /**
     * 逻辑删除
     */
    private Integer isDelete;


}
