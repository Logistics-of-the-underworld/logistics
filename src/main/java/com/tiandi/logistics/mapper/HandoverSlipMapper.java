package com.tiandi.logistics.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tiandi.logistics.entity.front.AddHandover;
import com.tiandi.logistics.entity.pojo.HandoverSlip;

import java.util.List;

/**
 * <p>
 * 交接单管理 Mapper 接口
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
public interface HandoverSlipMapper extends BaseMapper<HandoverSlip> {
    List<HandoverSlip> getHandover(String name_company);

    List<HandoverSlip> getHandoverByID(String name_distribution);

    int deleteHandover(String id_handover_order);
}
