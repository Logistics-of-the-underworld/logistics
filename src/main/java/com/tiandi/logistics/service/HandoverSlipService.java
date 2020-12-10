package com.tiandi.logistics.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.tiandi.logistics.entity.front.AddHandover;
import com.tiandi.logistics.entity.pojo.HandoverSlip;

import java.util.List;

/**
 * <p>
 * 交接单管理 服务类
 * </p>
 *
 * @author TP
 * @since 2020-11-25
 */
public interface HandoverSlipService extends IService<HandoverSlip> {
    List<HandoverSlip> getHandover(String name_distribution);

    List<HandoverSlip> getHandoverByID(String idPackage,String name_distribution);

    int updateHandover(HandoverSlip handoverSlip);

    int deleteHandover(String id_handover_order);
}
