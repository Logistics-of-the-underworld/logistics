package com.tiandi.logistics.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 配送范围管理
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("tp_distribution_range")
public class DistributionRange extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id_tp_distribution_range", type = IdType.AUTO)
    private Integer idTpDistributionRange;

    /**
     * 配送范围编码
     */
    private Integer idRange;

    /**
     * 配送点编码
     */
    private Integer idDistribution;

    /**
     * 范围名称
     */
    private String nameRange;

    /**
     * 开始配送时间
     */
    private LocalDateTime startDeliveryTime;

    /**
     * 结束配送时间
     */
    private LocalDateTime endDeliveryTime;

    /**
     * 备注
     */
    private String marks;

    /**
     * 逻辑删除
     */
    private Integer isDelete;


}
